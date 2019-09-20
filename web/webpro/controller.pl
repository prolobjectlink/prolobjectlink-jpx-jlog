:-consult('d:/pas-win32-x64-1.0.0/misc/http.pl').
:-consult('d:/pas-win32-x64-1.0.0/misc/integer.pl').
:-consult('d:/pas-win32-x64-1.0.0/web/webpro/model/webpro_address.pl').
:-consult('d:/pas-win32-x64-1.0.0/web/webpro/model/webpro_address_dao.pl').

webpro_index(ENTITY) :- 
	webpro_address(ENTITY),
	webpro_address_set_street(ENTITY, 'Marti # 349'),
	webpro_address_set_state(ENTITY, '123'),
	webpro_address_set_zip(ENTITY, '456'),
	webpro_address_set_city(ENTITY, 'Ciego de Avila'),
	webpro_address_set_country(ENTITY, 'Cuba'),
	webpro_address_create(ENTITY),
	render('view/index.html').
	
webpro_login(RESULT) :- 
	RESULT='administrador@server.com',
	render('view/login.html').

webpro_authentication(User,Password,RESULT) :- 
	RESULT=[User,Password],
	render('view/index.html').
webpro_authentication(_,_,RESULT) :- 
	RESULT='No valid user or password',
	render('view/login.html').
	
webpro_find_all_users(RESULT) :- 
	webpro_address_dao(DAO),
	webpro_address_dao_retrieve_all(DAO, RESULT),
	render('view/list.html').
	
webpro_query_all_users(RESULT) :- 
	webpro_address_dao(DAO),
	webpro_address_dao_query_all(DAO,'select a from WebproAddress a', RESULT),
	render('view/list.html').
	
webpro_find_all_users_range(MAX,FIRST,RESULT) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	webpro_address_dao(DAO),
	webpro_address_dao_retrieve_all(DAO,A,B,RESULT),
	render('view/list.html').
	
webpro_query_all_users_range(MAX,FIRST,RESULT) :- 
	integer_parse_int(INTEGER, MAX, A),
	integer_parse_int(INTEGER, FIRST, B),
	webpro_address_dao(DAO),
	webpro_address_dao_query_all(DAO,'select a from WebproAddress a',A,B,RESULT),
	render('view/list.html').
	
webpro_find_user(ID,RESULT) :- 
	integer_parse_int(ID, A),
	webpro_address_dao(DAO),
	webpro_address_dao_retrieve_one(DAO, A, RESULT),
	render('view/show.html').
	
webpro_query_user(ID,RESULT) :- 
	atom_concat('select a from WebproAddress a where a.id =', ID, QUERY),
	webpro_address_dao(DAO),
	webpro_address_dao_query_one(DAO, QUERY, RESULT),
	render('view/show.html').
	
webpro_update_user(ID,STREET,STATE,ZIP,CITY,COUNTRY,ENTITY) :- 
	integer_parse_int(ID, A),
	webpro_address_dao(DAO),
	webpro_address_dao_retrieve_one(DAO, A, ENTITY),
	webpro_address_set_street(ENTITY, STREET),
	webpro_address_set_state(ENTITY, STATE),
	webpro_address_set_zip(ENTITY, ZIP),
	webpro_address_set_city(ENTITY, CITY),
	webpro_address_set_country(ENTITY, COUNTRY),
	webpro_address_update(ENTITY),
	render('view/show.html').
	
webpro_create_user(STREET,STATE,ZIP,CITY,COUNTRY,ENTITY) :- 
	webpro_address(ENTITY),
	webpro_address_set_street(ENTITY, STREET),
	webpro_address_set_state(ENTITY, STATE),
	webpro_address_set_zip(ENTITY, ZIP),
	webpro_address_set_city(ENTITY, CITY),
	webpro_address_set_country(ENTITY, COUNTRY),
	webpro_address_create(ENTITY),
	render('view/show.html').
	
webpro_delete_user(ID,RESULT) :- 
	integer_parse_int(ID, A),
	webpro_address_dao(DAO),
	webpro_address_dao_retrieve_one(DAO, A, ENTITY),
	webpro_address_dao_delete(DAO, ENTITY),
	webpro_address_dao_retrieve_all(DAO, RESULT),
	render('view/list.html').