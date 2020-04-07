:-consult('../../../obj/prolobject.pl').

:-consult('../../../misc/pl/http.pl').

:-consult('../../../misc/pl/float.pl').

:-consult('../../../misc/pl/integer.pl').

:-consult('../../../web/webpro/model/webpro_person.pl').

webpro_person_new(_) :- 
	render('view/webpro_person/new.html').

webpro_person_edit(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	webpro_person_retrieve_one(A, ENTITY),
	render('view/webpro_person/edit.html').

webpro_person_find_all(LIST) :- 
	webpro_person_retrieve_all(LIST),
	render('view/webpro_person/list.html').

webpro_person_query_all(LIST) :- 
	webpro_person_query_all('select a from WebproPerson a', LIST),
	render('view/webpro_person/list.html').

webpro_person_find_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	webpro_person_retrieve_all(A, B, LIST),
	render('view/webpro_person/list.html').

webpro_person_query_all_range(MAX, FIRST, LIST) :- 
	integer_parse_int(MAX, A),
	integer_parse_int(FIRST, B),
	webpro_person_query_all('select a from WebproPerson a', A, B, LIST),
	render('view/webpro_person/list.html').

webpro_person_find(ID, ENTITY) :- 
	integer_parse_int(ID, A),
	webpro_person_retrieve_one(A, ENTITY),
	render('view/webpro_person/show.html').

webpro_person_query(ID, ENTITY) :- 
	atom_concat('select a from WebproPerson a where a.id =', ID, QUERY),
	webpro_person_query_one(QUERY, ENTITY),
	render('view/webpro_person/show.html').

webpro_person_update(LASTNAME, ADDRESS, SALT, IDNUMBER, PHOTO, WEIGHT, LOGINCOUNT, FIRSTNAME, PHONE, HEIGTH, MIDDLENAME, COMPANY, ID, PWD, USER, EMAIL, ENTITY) :- 
	integer_parse_int(ID, A),
	webpro_person_retrieve_one(A, ENTITY),
	webpro_person_set_last_name(ENTITY, LASTNAME),
	webpro_person_set_address(ENTITY, ADDRESS),
	webpro_person_set_salt(ENTITY, SALT),
	webpro_person_set_idnumber(ENTITY, IDNUMBER),
	webpro_person_set_photo(ENTITY, PHOTO),
	float_parse_float(WEIGHT, WEIGHT_FLOAT_VALUE),
	webpro_person_set_weight(ENTITY, WEIGHT_FLOAT_VALUE),
	integer_parse_int(LOGINCOUNT, LOGINCOUNT_INT_VALUE),
	webpro_person_set_login_count(ENTITY, LOGINCOUNT_INT_VALUE),
	webpro_person_set_first_name(ENTITY, FIRSTNAME),
	webpro_person_set_phone(ENTITY, PHONE),
	float_parse_float(HEIGTH, HEIGTH_FLOAT_VALUE),
	webpro_person_set_heigth(ENTITY, HEIGTH_FLOAT_VALUE),
	webpro_person_set_middle_name(ENTITY, MIDDLENAME),
	webpro_person_set_company(ENTITY, COMPANY),
	webpro_person_set_pwd(ENTITY, PWD),
	webpro_person_set_user(ENTITY, USER),
	webpro_person_set_email(ENTITY, EMAIL),
	webpro_person_update(ENTITY),
	render('view/webpro_person/show.html').

webpro_person_create(LASTNAME, ADDRESS, SALT, IDNUMBER, PHOTO, WEIGHT, LOGINCOUNT, FIRSTNAME, PHONE, HEIGTH, MIDDLENAME, COMPANY, PWD, USER, EMAIL, ENTITY) :- 
	webpro_person(ENTITY),
	webpro_person_set_last_name(ENTITY, LASTNAME),
	webpro_person_set_address(ENTITY, ADDRESS),
	webpro_person_set_salt(ENTITY, SALT),
	webpro_person_set_idnumber(ENTITY, IDNUMBER),
	webpro_person_set_photo(ENTITY, PHOTO),
	float_parse_float(WEIGHT, WEIGHT_FLOAT_VALUE),
	webpro_person_set_weight(ENTITY, WEIGHT_FLOAT_VALUE),
	integer_parse_int(LOGINCOUNT, LOGINCOUNT_INT_VALUE),
	webpro_person_set_login_count(ENTITY, LOGINCOUNT_INT_VALUE),
	webpro_person_set_first_name(ENTITY, FIRSTNAME),
	webpro_person_set_phone(ENTITY, PHONE),
	float_parse_float(HEIGTH, HEIGTH_FLOAT_VALUE),
	webpro_person_set_heigth(ENTITY, HEIGTH_FLOAT_VALUE),
	webpro_person_set_middle_name(ENTITY, MIDDLENAME),
	webpro_person_set_company(ENTITY, COMPANY),
	webpro_person_set_pwd(ENTITY, PWD),
	webpro_person_set_user(ENTITY, USER),
	webpro_person_set_email(ENTITY, EMAIL),
	webpro_person_create(ENTITY),
	render('view/webpro_person/show.html').

webpro_person_delete(ID, LIST) :- 
	integer_parse_int(ID, A),
	webpro_person_retrieve_one(A, ENTITY),
	webpro_person_delete(ENTITY),
	webpro_person_retrieve_all(LIST),
	render('view/webpro_person/list.html').

