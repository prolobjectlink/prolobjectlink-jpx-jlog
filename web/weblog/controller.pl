:-consult('d:/pas-win32-x64-1.0.0/misc/http.pl').
:-consult('d:/pas-win32-x64-1.0.0/misc/integer.pl').
:-consult('d:/pas-win32-x64-1.0.0/web/weblog/model/weblog_address.pl').
:-consult('d:/pas-win32-x64-1.0.0/web/weblog/model/weblog_address_dao.pl').

weblog_index(ENTITY) :- 
	weblog_address(ENTITY),
	weblog_address_set_street(ENTITY, 'Marti # 349'),
	weblog_address_set_state(ENTITY, '123'),
	weblog_address_set_zip(ENTITY, '456'),
	weblog_address_set_city(ENTITY, 'Ciego de Avila'),
	weblog_address_set_country(ENTITY, 'Cuba'),
	weblog_address_create(ENTITY),
	render('view/index.html').
	
weblog_login(RESULT) :- 
	RESULT='administrador@server.com',
	render('view/login.html').

weblog_authentication(User,Password,RESULT) :- 
	RESULT=[User,Password],
	render('view/index.html').
weblog_authentication(_,_,RESULT) :- 
	RESULT='No valid user or password',
	render('view/login.html').
	
weblog_find_all_users(RESULT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_all(DAO, RESULT),
	render('view/list.html').
	
weblog_query_all_users(RESULT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_query_all(DAO,'select a from WeblogAddress a', RESULT),
	render('view/list.html').
	
weblog_find_all_users_range(MAX,FIRST,RESULT) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_all(DAO,A,B,RESULT),
	render('view/list.html').
	
weblog_query_all_users_range(MAX,FIRST,RESULT) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_address_dao(DAO),
	weblog_address_dao_query_all(DAO,'select a from WeblogAddress a',A,B,RESULT),
	render('view/list.html').
	
weblog_find_user(ID,RESULT) :- 
	integer_parse_int(ID, A),
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_one(DAO, A, RESULT),
	render('view/show.html').
	
weblog_query_user(ID,RESULT) :- 
	atom_concat('select a from WeblogAddress a where a.id =', ID, QUERY),
	weblog_address_dao(DAO),
	weblog_address_dao_query_one(DAO, QUERY, RESULT),
	render('view/show.html').
	
weblog_update_user(ID,STREET,STATE,ZIP,CITY,COUNTRY,ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_one(DAO, A, ENTITY),
	weblog_address_set_street(ENTITY, STREET),
	weblog_address_set_state(ENTITY, STATE),
	weblog_address_set_zip(ENTITY, ZIP),
	weblog_address_set_city(ENTITY, CITY),
	weblog_address_set_country(ENTITY, COUNTRY),
	weblog_address_update(ENTITY),
	render('view/show.html').
	
weblog_create_user(STREET,STATE,ZIP,CITY,COUNTRY,ENTITY) :- 
	weblog_address(ENTITY),
	weblog_address_set_street(ENTITY, STREET),
	weblog_address_set_state(ENTITY, STATE),
	weblog_address_set_zip(ENTITY, ZIP),
	weblog_address_set_city(ENTITY, CITY),
	weblog_address_set_country(ENTITY, COUNTRY),
	weblog_address_create(ENTITY),
	render('view/show.html').
	
weblog_delete_user(ID,RESULT) :- 
	integer_parse_int(ID, A),
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_one(DAO, A, ENTITY),
	weblog_address_dao_delete(DAO, ENTITY),
	weblog_address_dao_retrieve_all(DAO, RESULT),
	render('view/list.html').