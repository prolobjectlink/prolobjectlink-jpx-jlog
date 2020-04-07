:-multifile(index/1).

:-consult('../../../misc/pl/http.pl').

index(_) :- 
	render('index.html').