:-consult('../../../obj/prolobject.pl').

:-consult('../../../misc/pl/http.pl').

:-consult('../../../misc/pl/float.pl').

:-consult('../../../misc/pl/integer.pl').

:-consult('../../../web/weblog/model/weblog_person.pl').

weblog_person_new(_) :- 
	render('view/weblog_person/new.html').

weblog_person_edit(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_person_retrieve_one(A, ENTITY),
	render('view/weblog_person/edit.html').

weblog_person_find_all(LIST) :- 
	weblog_person_retrieve_all(LIST),
	render('view/weblog_person/list.html').

weblog_person_query_all(LIST) :- 
	weblog_person_query_all('select a from WeblogPerson a', LIST),
	render('view/weblog_person/list.html').

weblog_person_find_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_person_retrieve_all(A, B, LIST),
	render('view/weblog_person/list.html').

weblog_person_query_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	weblog_person_query_all('select a from WeblogPerson a', A, B, LIST),
	render('view/weblog_person/list.html').

weblog_person_find(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_person_retrieve_one(A, ENTITY),
	render('view/weblog_person/show.html').

weblog_person_query(ID, ENTITY) :- 
	atom_concat('select a from WeblogPerson a where a.id =', ID, QUERY),
	weblog_person_query_one(QUERY, ENTITY),
	render('view/weblog_person/show.html').

weblog_person_update(LASTNAME, ADDRESS, SALT, IDNUMBER, PHOTO, WEIGHT, LOGINCOUNT, FIRSTNAME, PHONE, HEIGTH, MIDDLENAME, COMPANY, ID, PWD, USER, EMAIL, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_person_retrieve_one(A, ENTITY),
	weblog_person_set_last_name(ENTITY, LASTNAME),
	weblog_person_set_address(ENTITY, ADDRESS),
	weblog_person_set_salt(ENTITY, SALT),
	weblog_person_set_idnumber(ENTITY, IDNUMBER),
	weblog_person_set_photo(ENTITY, PHOTO),
	float_parse_float(WEIGHT, WEIGHT_FLOAT_VALUE),
	weblog_person_set_weight(ENTITY, WEIGHT_FLOAT_VALUE),
	integer_parse_int(LOGINCOUNT, LOGINCOUNT_INT_VALUE),
	weblog_person_set_login_count(ENTITY, LOGINCOUNT_INT_VALUE),
	weblog_person_set_first_name(ENTITY, FIRSTNAME),
	weblog_person_set_phone(ENTITY, PHONE),
	float_parse_float(HEIGTH, HEIGTH_FLOAT_VALUE),
	weblog_person_set_heigth(ENTITY, HEIGTH_FLOAT_VALUE),
	weblog_person_set_middle_name(ENTITY, MIDDLENAME),
	weblog_person_set_company(ENTITY, COMPANY),
	weblog_person_set_pwd(ENTITY, PWD),
	weblog_person_set_user(ENTITY, USER),
	weblog_person_set_email(ENTITY, EMAIL),
	weblog_person_update(ENTITY),
	render('view/weblog_person/show.html').

weblog_person_create(LASTNAME, ADDRESS, SALT, IDNUMBER, PHOTO, WEIGHT, LOGINCOUNT, FIRSTNAME, PHONE, HEIGTH, MIDDLENAME, COMPANY, PWD, USER, EMAIL, ENTITY) :- 
	weblog_person(ENTITY),
	weblog_person_set_last_name(ENTITY, LASTNAME),
	weblog_person_set_address(ENTITY, ADDRESS),
	weblog_person_set_salt(ENTITY, SALT),
	weblog_person_set_idnumber(ENTITY, IDNUMBER),
	weblog_person_set_photo(ENTITY, PHOTO),
	float_parse_float(WEIGHT, WEIGHT_FLOAT_VALUE),
	weblog_person_set_weight(ENTITY, WEIGHT_FLOAT_VALUE),
	integer_parse_int(LOGINCOUNT, LOGINCOUNT_INT_VALUE),
	weblog_person_set_login_count(ENTITY, LOGINCOUNT_INT_VALUE),
	weblog_person_set_first_name(ENTITY, FIRSTNAME),
	weblog_person_set_phone(ENTITY, PHONE),
	float_parse_float(HEIGTH, HEIGTH_FLOAT_VALUE),
	weblog_person_set_heigth(ENTITY, HEIGTH_FLOAT_VALUE),
	weblog_person_set_middle_name(ENTITY, MIDDLENAME),
	weblog_person_set_company(ENTITY, COMPANY),
	weblog_person_set_pwd(ENTITY, PWD),
	weblog_person_set_user(ENTITY, USER),
	weblog_person_set_email(ENTITY, EMAIL),
	weblog_person_create(ENTITY),
	render('view/weblog_person/show.html').

weblog_person_delete(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	weblog_person_retrieve_one(A, ENTITY),
	weblog_person_delete(ENTITY),
	weblog_person_retrieve_all(ENTITY),
	render('view/weblog_person/list.html').

