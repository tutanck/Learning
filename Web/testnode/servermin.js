var http = require('http');

var server = http.createServer(function(req, res) {
  res.writeHead(200);
  res.end('Salut tout le monde !');
});

server.listen(8080); // Démarre le serveur
console.log("j'ecoute sur 8080")
