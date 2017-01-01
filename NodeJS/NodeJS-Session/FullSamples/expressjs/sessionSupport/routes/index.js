/*
 * GET home page.
 */

var onlineUsers = new Array ( 'Shereef' , 'Akram' , 'Ayman' ) ;
//var onlineUsers = new Array () ;

exports.index = function(req, res){
  req.session.users = onlineUsers ;
  res.render('index', { title: 'Express' , users : new Array () }) ;
};

exports.loadFriends = function ( req , res , next) {
	req.users = req.session.users.slice ( 0 , 1 ) ;
	next () ;
}

exports.home = function(req, res){
  console.log ( req.param ( 'username' ) ) ;
  console.log ( req.param ( 'password' ) ) ;
  res.render('home', { title : 'Home Page' , username: req.param ( 'username' ) , users : req.session.users }) ;
};
