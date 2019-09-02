:-consult('../../obj/prolobject.pl').

render(_).

index(Result) :- 
	Result='Hello World',
	render('view/index.html').
	
login(Result) :- 
	Result='administrador@server.com',
	render('view/login.html').

authentication(User,Password,Result) :- 
	Result=[User,Password],
	render('view/index.html').
authentication(_,_,Result) :- 
	Result='No valid user or password',
	render('view/login.html').
	
findAllUsers(Result) :- 
	Result=[jzalacain,rshasat,rllanes,rcadet],
	render('view/list.html').
	
findUserById(Id,Result) :- 
	Result=Id,
	render('view/show.html').
	
saveUser(Name,User,Age,Result) :- 
	Result=[Name,User,Age],
	render('view/show.html').
	
deleteUser(Id,Result) :- 
	Result=Id,
	render('view/list.html').