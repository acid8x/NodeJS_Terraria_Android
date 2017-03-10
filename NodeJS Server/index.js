var
  express = require('express'),
  app = express(),
  io,
  http,
  id = 0;
  
http = require('http').createServer(app);
io = require('socket.io')(http, { pingInterval: 500 });

http.listen(2222, function () {
  console.log('socket.io server listening on port', 2222);
});

io.on('connection', function(socket) {
	socket.Ident = id++;
	console.log(socket.Ident + " connected");
		 
	socket.on('message', function(data) {
		socket.broadcast.emit('message', data);
	});
		
	socket.on('disconnect', function() {
		console.log(socket.Ident + " disconnected");
  });
});