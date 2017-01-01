var http = require ( 'http' );

http.createServer ( function ( req , res ) {
	var count = 0 ;
	res.writeHead ( 200 , { 'content-type' : 'text/plain' } ) ;
	res.write ( 'Hello\n' ) ;
	setInterval ( function () {
		res.write ( 'Tweet #' + ++count + '\n' ) ;
	} , 2000 ) ;
}
).listen ( 9999 )  ;

