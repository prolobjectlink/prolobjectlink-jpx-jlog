:-consult('../../../obj/prolobject.pl').

:-consult('../../../misc/pl/http.pl').

:-consult('../../../misc/pl/float.pl').

:-consult('../../../misc/pl/integer.pl').

:-consult('../../../web/webpro/model/webpro_department.pl').

webpro_department_new(_) :- 
	render('view/webpro_department/new.html').

webpro_department_edit(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	webpro_department_retrieve_one(A, ENTITY),
	render('view/webpro_department/edit.html').

webpro_department_find_all(LIST) :- 
	webpro_department_retrieve_all(LIST),
	render('view/webpro_department/list.html').

webpro_department_query_all(LIST) :- 
	webpro_department_query_all('select a from WebproDepartment a', LIST),
	render('view/webpro_department/list.html').

webpro_department_find_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	webpro_department_retrieve_all(A, B, LIST),
	render('view/webpro_department/list.html').

webpro_department_query_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	webpro_department_query_all('select a from WebproDepartment a', A, B, LIST),
	render('view/webpro_department/list.html').

webpro_department_find(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	webpro_department_retrieve_one(A, ENTITY),
	render('view/webpro_department/show.html').

webpro_department_query(ID, ENTITY) :- 
	atom_concat('select a from WebproDepartment a where a.id =', ID, QUERY),
	webpro_department_query_one(QUERY, ENTITY),
	render('view/webpro_department/show.html').

webpro_department_update(NAME, ID, ENTITY) :- 
	integer_parse_int(ID, A),
	webpro_department_retrieve_one(A, ENTITY),
	webpro_department_set_name(ENTITY, NAME),
	webpro_department_update(ENTITY),
	render('view/webpro_department/show.html').

webpro_department_create(NAME, ENTITY) :- 
	webpro_department(ENTITY),
	webpro_department_set_name(ENTITY, NAME),
	webpro_department_create(ENTITY),
	render('view/webpro_department/show.html').

webpro_department_delete(ID, LIST) :- 
	integer_parse_int(ID, A),
	webpro_department_retrieve_one(A, ENTITY),
	webpro_department_delete(ENTITY),
	webpro_department_retrieve_all(LIST),
	render('view/webpro_department/list.html').

