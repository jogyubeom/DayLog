import 'package:flutter/material.dart';
import 'package:loveclip/widgets/button_widget.dart';
import 'package:loveclip/widgets/input_widget.dart';
import 'package:loveclip/screens/signup_screen.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController _idController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _rememberId = false; // 체크박스 상태를 저장하는 변수

  // 입력된 아이디와 비밀번호를 처리하는 함수
  void _login() {
    String id = _idController.text;
    String password = _passwordController.text;
    print('로그인 아이디: $id');
    print('로그인 비밀번호: $password');
    // 여기서 서버로 데이터를 전송하거나 다른 처리 로직을 추가할 수 있습니다.
  }

  @override
  void dispose() {
    _idController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: const Color(0xFFFFFFFF),
        body: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 40),
          child: Column(
            children: [
              const SizedBox(
                height: 120,
              ),
              const Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Image(
                    image: AssetImage('assets/Logo.png'),
                    width: 250,
                    height: 250,
                  ),
                ],
              ),

              const SizedBox(height: 30),

              // 로그인 아이디 입력 필드
              InputWidget(
                idController: _idController,
                text: 'ID',
                bgColor: const Color(0xFFEDEDED),
                obscure: false,
              ),

              // 체크박스와 링크들
              SizedBox(
                width: double.infinity,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Row(
                      children: [
                        Checkbox(
                          activeColor: const Color(0xFFFFAFAF),
                          value: _rememberId,
                          onChanged: (bool? value) {
                            setState(() {
                              _rememberId = value ?? false;
                            });
                          },
                        ),
                        const Text(
                          'Remember ID',
                          style: TextStyle(
                            color: Color(0xFFFFAFAF),
                          ),
                        ),
                      ],
                    ),
                    const Row(
                      children: [
                        Text(
                          'ID 찾기',
                          style: TextStyle(
                            color: Color(0xFFFFAFAF),
                          ),
                        ),
                        SizedBox(width: 5), // 텍스트 간의 간격 조정
                        Text(
                          '/',
                          style: TextStyle(
                            color: Color(0xFFFFAFAF),
                          ),
                        ),
                        SizedBox(width: 5), // 텍스트 간의 간격 조정
                        Text(
                          '비밀번호 찾기',
                          style: TextStyle(
                            color: Color(0xFFFFAFAF),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),

              const SizedBox(height: 10), // 높이 간격 조정

              // 비밀번호 입력 필드
              InputWidget(
                idController: _passwordController,
                text: 'PASSWORD',
                bgColor: const Color(0xFFEDEDED),
                obscure: true,
              ),

              const SizedBox(height: 20), // 로그인 버튼과의 간격 조정

              // 로그인 버튼
              ButtonWidget(
                onPressed: _login,
                text: '로그인',
                foreColor: const Color(0xFFFFFFFF),
                backColor: const Color(0xFFFFAFAF),
              ),

              const SizedBox(
                height: 20,
              ),

              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Row(
                    children: [
                      Text(
                        'Don\'t have an account?  ',
                        style: TextStyle(
                          color: Color(0xFF999999),
                        ),
                      ),
                    ],
                  ),
                  Row(
                    children: [
                      TextButton(
                        onPressed: () {
                          // 회원가입 페이지로 이동하는 코드
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => const SignupScreen(),
                            ),
                          );
                        },
                        child: const Text(
                          'Sign Up!',
                          style: TextStyle(
                            color: Color(0xFFFFAFAF),
                          ),
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
