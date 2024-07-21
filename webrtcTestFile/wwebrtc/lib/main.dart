import 'package:flutter/material.dart';
import 'package:flutter_webrtc/flutter_webrtc.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: VideoCallScreen(),
    );
  }
}

class VideoCallScreen extends StatefulWidget {
  const VideoCallScreen({super.key});

  @override
  VideoCallScreenState createState() => VideoCallScreenState();
}

class VideoCallScreenState extends State<VideoCallScreen> {
  final _localRenderer = RTCVideoRenderer();
  final _remoteRenderer = RTCVideoRenderer();
  RTCPeerConnection? _peerConnection;
  MediaStream? _localStream;
  WebSocketChannel? _channel;

  @override
  void initState() {
    super.initState();
    initRenderers();
    _requestPermissions().then((_) {
      _connectToSignalingServer();
      _createPeerConnection();
    });
  }

  Future<void> _requestPermissions() async {
    await [Permission.camera, Permission.microphone].request();
  }

  void _connectToSignalingServer() {
    try {
      print('Connecting to WebSocket server...');
      _channel = WebSocketChannel.connect(Uri.parse('ws://192.168.0.4:8080'));

      _channel?.stream.listen((message) {
        print('Message received: $message');
        _onMessageReceived(message);
      }, onError: (error) {
        print('WebSocket error: $error');
      }, onDone: () {
        print('WebSocket connection closed');
      });

      print('WebSocket connected');
    } catch (e) {
      print('WebSocket connection error: $e');
    }
  }

  void _onMessageReceived(message) async {
    try {
      // 메시지를 Uint8Array에서 String으로 변환
      String messageStr = String.fromCharCodes(message);

      var data = messageStr.split('|');

      if (data[0] == 'offer') {
        _receiveCall(data[1]); // Offer 메시지를 받으면 _receiveCall 호출
      } else if (data[0] == 'answer') {
        var description = RTCSessionDescription(data[1], 'answer');
        await _peerConnection?.setRemoteDescription(description);
      } else if (data[0] == 'candidate') {
        var candidate = RTCIceCandidate(data[1], data[2], int.parse(data[3]));
        await _peerConnection?.addCandidate(candidate);
      }
    } catch (e) {
      print('Error processing message: $e');
    }
  }

  @override
  void dispose() {
    _localRenderer.dispose();
    _remoteRenderer.dispose();
    _peerConnection?.dispose();
    _channel?.sink.close();
    super.dispose();
  }

  void initRenderers() async {
    await _localRenderer.initialize();
    await _remoteRenderer.initialize();
  }

  Future<void> _createPeerConnection() async {
    try {
      _localStream = await _getUserMedia();
      _localRenderer.srcObject = _localStream;

      final config = {
        'iceServers': [
          {'urls': 'stun:stun.l.google.com:19302'},
        ]
      };

      _peerConnection = await createPeerConnection(config);
      _peerConnection?.addStream(_localStream!);

      _peerConnection?.onIceCandidate = (candidate) {
        _channel?.sink.add(
          'candidate|${candidate.candidate}|${candidate.sdpMid}|${candidate.sdpMlineIndex}',
        );
      };

      _peerConnection?.onAddStream = (stream) {
        _remoteRenderer.srcObject = stream;
      };
    } catch (e) {
      print('Error creating peer connection: $e');
    }
  }

  Future<MediaStream> _getUserMedia() async {
    final Map<String, dynamic> mediaConstraints = {
      'audio': true,
      'video': {
        'mandatory': {
          'minWidth': '640',
          'minHeight': '480',
          'minFrameRate': '30',
        },
        'facingMode': 'user',
        'optional': [],
      }
    };

    try {
      return await navigator.mediaDevices.getUserMedia(mediaConstraints);
    } catch (e) {
      print('Error getting user media: $e');
      rethrow;
    }
  }

  void _makeCall() async {
    print('Making call...');
    var description = await _peerConnection?.createOffer();
    if (description != null) {
      String sdp = description.sdp ?? "";
      sdp += 'a=rtcp-mux\r\n'; // RTCP-MUX 추가
      await _peerConnection
          ?.setLocalDescription(RTCSessionDescription(sdp, description.type));
      _channel?.sink.add('offer|$sdp');
      print('Offer sent: $sdp');
    } else {
      print('Error creating offer');
    }
  }

  void _receiveCall(String sdp) async {
    print('Receive call with SDP: $sdp');
    if (_peerConnection?.signalingState ==
        RTCSignalingState.RTCSignalingStateHaveLocalOffer) {
      await _peerConnection
          ?.setRemoteDescription(RTCSessionDescription('', 'rollback'));
    }
    sdp += 'a=rtcp-mux\r\n'; // RTCP-MUX 추가
    var description = RTCSessionDescription(sdp, 'offer');
    await _peerConnection?.setRemoteDescription(description);
    var answer = await _peerConnection?.createAnswer();
    if (answer != null) {
      String answerSdp = answer.sdp ?? "";
      answerSdp += 'a=rtcp-mux\r\n'; // RTCP-MUX 추가
      await _peerConnection
          ?.setLocalDescription(RTCSessionDescription(answerSdp, answer.type));
      _channel?.sink.add('answer|$answerSdp');
      print('Answer sent: $answerSdp');
    } else {
      print('Error creating answer');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Video Call'),
      ),
      body: Column(
        children: [
          Expanded(
            child: RTCVideoView(_localRenderer, mirror: true),
          ),
          Expanded(
            child: RTCVideoView(_remoteRenderer),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                onPressed: _makeCall,
                child: const Text('Make Call'),
              ),
              ElevatedButton(
                onPressed: () {
                  // 임시로 유효한 SDP 값을 사용합니다.
                  String dummySdp =
                      'v=0\r\no=- 46117303 2 IN IP4 127.0.0.1\r\ns=-\r\nc=IN IP4 127.0.0.1\r\nt=0 0\r\nm=audio 5004 RTP/AVP 0\r\na=rtpmap:0 PCMU/8000\r\na=ice-ufrag:shQn\r\na=ice-pwd:IB1Cw7BmZDKBHpOlSg6R53Qb\r\na=fingerprint:sha-256 5D:2B:CF:F1:0D:98:62:0A:EA:E7:63:E0:B0:05:51:66:4C:08:8A:3E:5F:66:B7:94:29:F5:70:93:B3:7E:84:E5\r\n';
                  _receiveCall(dummySdp);
                },
                child: const Text('Receive Call'),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
