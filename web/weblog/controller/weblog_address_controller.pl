:-consult('../../../obj/prolobject.pl').

:-consult('../../../misc/pl/http.pl').

:-consult('../../../misc/pl/float.pl').

:-consult('../../../misc/pl/integer.pl').

:-consult('../../../web/weblog/model/weblog_address.pl').

weblog_address_new(_) :- 
	render('view/weblog_address/new.html').

weblog_address_edit(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_address_retrieve_one(A, ENTITY),
	render('view/weblog_address/edit.html').

weblog_address_find_all(LIST) :- 
	weblog_address_retrieve_all(LIST),
	render('view/weblog_address/list.html').

weblog_address_query_all(LIST) :- 
	weblog_address_query_all('select a from WeblogAddress a', LIST),
	render('view/weblog_address/list.html').

weblog_address_find_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_address_retrieve_all(A, B, LIST),
	render('view/weblog_address/list.html').

weblog_address_query_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_address_query_all('select a from WeblogAddress a', A, B, LIST),
	render('view/weblog_address/list.html').

weblog_address_find(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_address_retrieve_one(A, ENTITY),
	render('view/weblog_address/show.html').

weblog_address_query(ID, ENTITY) :- 
	atom_concat('select a from WeblogAddress a where a.id =', ID, QUERY),
	weblog_address_query_one(QUERY, ENTITY),
	render('view/weblog_address/show.html').

weblog_address_update(ZIP, COUNTRY, CITY, STREET, ID, STATE, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_address_retrieve_one(A, ENTITY),
	weblog_address_set_zip(ENTITY, ZIP),
	weblog_address_set_country(ENTITY, COUNTRY),
	weblog_address_set_city(ENTITY, CITY),
	weblog_address_set_street(ENTITY, STREET),
	weblog_address_set_state(ENTITY, STATE),
	weblog_address_update(ENTITY),
	render('view/weblog_address/show.html').

weblog_address_create(ZIP, COUNTRY, CITY, STREET, STATE, ENTITY) :- 
	weblog_address(ENTITY),
	weblog_address_set_zip(ENTITY, ZIP),
	weblog_address_set_country(ENTITY, COUNTRY),
	weblog_address_set_city(ENTITY, CITY),
	weblog_address_set_street(ENTITY, STREET),
	weblog_address_set_state(ENTITY, STATE),
	weblog_address_create(ENTITY),
	render('view/weblog_address/show.html').

weblog_address_delete(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_address_retrieve_one(A, ENTITY),
	weblog_address_delete(ENTITY),
	weblog_address_retrieve_all(ENTITY),
	render('view/weblog_address/list.html').

