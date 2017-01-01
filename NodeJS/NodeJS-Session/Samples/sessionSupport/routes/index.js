
/*
 * GET home page.
 */

exports.index = function(req, res){
	var onlineFriends = new Array( 'Shereef'  , 'Akram' , 'Fouad' ) ;
	req.session.friends = onlineFriends ;
  res.render('index', { title: 'Express' })
};

exports.loadFriends = function ( req, res , next ) {
		var onlineFriends = new Array( 'Amira'  , 'Nancy' , 'Hoda' ) ;
req.friends = onlineFriends ;
next () ;
}

exports.home = function(req, res){
  res.render('home', { title: req.param ( 'username' ) , friends : req.friends })
};


