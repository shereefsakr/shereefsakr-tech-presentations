var http = require ( 'http');

// Then put the variable in the right place.
var count = 0 ;

http.createServer ( function ( req , res ) {
	res.writeHead ( 200 , { 'content-type' : 'text/plain' } ) ;
	res.write ( 'Tweets\n' ) ;

	setInterval ( function () {
		res.write ( 'Tweet #' + ++count  + '\n' ) ;
	} , 2000 ) ;

	setTimeout ( function () {
		res.end ( 'Last Tweet.\n' ) ;
	} , 15000 ) ;
}
).listen ( 9999 )  ;
