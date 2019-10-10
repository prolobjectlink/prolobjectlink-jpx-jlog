:-consult('../../../obj/prolobject.pl').

:-consult('../../../misc/pl/http.pl').

:-consult('../../../misc/pl/float.pl').

:-consult('../../../misc/pl/integer.pl').

:-consult('../../../web/weblog/model/weblog_department.pl').

weblog_department_new(_) :- 
	render('view/weblog_department/new.html').

weblog_department_edit(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_department_retrieve_one(A, ENTITY),
	render('view/weblog_department/edit.html').

weblog_department_find_all(LIST) :- 
	weblog_department_retrieve_all(LIST),
	render('view/weblog_department/list.html').

weblog_department_query_all(LIST) :- 
	weblog_department_query_all('select a from WeblogDepartment a', LIST),
	render('view/weblog_department/list.html').

weblog_department_find_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_department_retrieve_all(A, B, LIST),
	render('view/weblog_department/list.html').

weblog_department_query_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_department_query_all('select a from WeblogDepartment a', A, B, LIST),
	render('view/weblog_department/list.html').

weblog_department_find(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_department_retrieve_one(A, ENTITY),
	render('view/weblog_department/show.html').

weblog_department_query(ID, ENTITY) :- 
	atom_concat('select a from WeblogDepartment a where a.id =', ID, QUERY),
	weblog_department_query_one(QUERY, ENTITY),
	render('view/weblog_department/show.html').

weblog_department_update(NAME, ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_department_retrieve_one(A, ENTITY),
	weblog_department_set_name(ENTITY, NAME),
	weblog_department_update(ENTITY),
	render('view/weblog_department/show.html').

weblog_department_create(NAME, ENTITY) :- 
	weblog_department(ENTITY),
	weblog_department_set_name(ENTITY, NAME),
	weblog_department_create(ENTITY),
	render('view/weblog_department/show.html').

weblog_department_delete(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_department_retrieve_one(A, ENTITY),
	weblog_department_delete(ENTITY),
	weblog_department_retrieve_all(ENTITY),
	render('view/weblog_department/list.html').

