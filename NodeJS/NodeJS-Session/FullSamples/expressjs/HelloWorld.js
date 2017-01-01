
/**
 * Module dependencies.
 */

var express = require('express');

var app = express.createServer();

app.get('/', function(req, res){
  res.send('Hello World');
});

app.listen(9999);
console.log('Express started on port 3000');
