const WebSocket = require('ws');

const port = 8080;
const server = new WebSocket.Server({ port });

server.on('connection', socket => {
    console.log('Client connected');

    socket.on('message', message => {
        console.log(`Received message => ${message}`);
        // Echo the message back to the client
        server.clients.forEach(client => {
            if (client !== socket && client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        });
    });

    socket.on('close', () => {
        console.log('Client disconnected');
    });
});

console.log(`Server is running on ws://192.168.0.4:${port}`);
