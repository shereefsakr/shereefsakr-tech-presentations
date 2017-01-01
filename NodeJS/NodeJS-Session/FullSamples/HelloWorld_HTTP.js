var http = require ( 'http' );

http.createServer ( function ( req , res ) {
	res.writeHead ( 200 , { 'content-type' : 'text/plain' } ) ;
	res.write ( 'Hello\n' ) ;
	setInterval ( function () {
		res.end ( 'World !\n') ;
	} , 2000 ) ;
}
).listen ( 9999 )  ;
