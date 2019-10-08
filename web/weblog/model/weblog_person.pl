% Copyright (c) 2019 Prolobjectlink Project

% Permission is hereby granted, free of charge, to any person obtaining a copy
% of this software and associated documentation files (the "Software"), to deal
% in the Software without restriction, including without limitation the rights
% to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
% copies of the Software, and to permit persons to whom the Software is
% furnished to do so, subject to the following conditions:

% The above copyright notice and this permission notice shall be included in
% all copies or substantial portions of the Software.

% THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
% IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
% FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
% AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
% LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
% OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
% THE SOFTWARE.

% Author: Jose Zalacain

:-consult('../../../obj/prolobject.pl').

:-consult('../../../web/weblog/model/weblog_person_dao.pl').

weblog_person(OUT) :- 
	object_new('weblog.model.WeblogPerson', [], OUT).

weblog_person_equals(REF, ARG0, OUT) :- 
	object_call(REF, equals, '.'(ARG0, []), OUT).

weblog_person_get_id(REF, OUT) :- 
	object_call(REF, getId, [], OUT).

weblog_person_get_first_name(REF, OUT) :- 
	object_call(REF, getFirstName, [], OUT).

weblog_person_to_string(REF, OUT) :- 
	object_call(REF, toString, [], OUT).

weblog_person_set_salt(REF, ARG0) :- 
	object_call(REF, setSalt, '.'(ARG0, []), _).

weblog_person_set_address(REF, ARG0) :- 
	object_call(REF, setAddress, '.'(ARG0, []), _).

weblog_person_get_company(REF, OUT) :- 
	object_call(REF, getCompany, [], OUT).

weblog_person_get_pwd(REF, OUT) :- 
	object_call(REF, getPwd, [], OUT).

weblog_person_get_email(REF, OUT) :- 
	object_call(REF, getEmail, [], OUT).

weblog_person_wait(REF, ARG0, ARG1) :- 
	object_call(REF, wait, '.'(ARG0, '.'(ARG1, [])), _).

weblog_person_wait(REF, ARG0) :- 
	object_call(REF, wait, '.'(ARG0, []), _).

weblog_person_wait(REF) :- 
	object_call(REF, wait, [], _).

weblog_person_notify(REF) :- 
	object_call(REF, notify, [], _).

weblog_person_set_photo(REF, ARG0) :- 
	object_call(REF, setPhoto, '.'(ARG0, []), _).

weblog_person_set_phone(REF, ARG0) :- 
	object_call(REF, setPhone, '.'(ARG0, []), _).

weblog_person_delete(REF) :- 
	object_call(REF, delete, [], _).

weblog_person_get_user(REF, OUT) :- 
	object_call(REF, getUser, [], OUT).

weblog_person_get_photo(REF, OUT) :- 
	object_call(REF, getPhoto, [], OUT).

weblog_person_set_idnumber(REF, ARG0) :- 
	object_call(REF, setIdnumber, '.'(ARG0, []), _).

weblog_person_set_last_name(REF, ARG0) :- 
	object_call(REF, setLastName, '.'(ARG0, []), _).

weblog_person_get_phone(REF, OUT) :- 
	object_call(REF, getPhone, [], OUT).

weblog_person_get_address(REF, OUT) :- 
	object_call(REF, getAddress, [], OUT).

weblog_person_set_company(REF, ARG0) :- 
	object_call(REF, setCompany, '.'(ARG0, []), _).

weblog_person_get_middle_name(REF, OUT) :- 
	object_call(REF, getMiddleName, [], OUT).

weblog_person_hash_code(REF, OUT) :- 
	object_call(REF, hashCode, [], OUT).

weblog_person_get_class(REF, OUT) :- 
	object_call(REF, getClass, [], OUT).

weblog_person_set_first_name(REF, ARG0) :- 
	object_call(REF, setFirstName, '.'(ARG0, []), _).

weblog_person_get_last_name(REF, OUT) :- 
	object_call(REF, getLastName, [], OUT).

weblog_person_create(REF) :- 
	object_call(REF, create, [], _).

weblog_person_set_email(REF, ARG0) :- 
	object_call(REF, setEmail, '.'(ARG0, []), _).

weblog_person_notify_all(REF) :- 
	object_call(REF, notifyAll, [], _).

weblog_person_update(REF) :- 
	object_call(REF, update, [], _).

weblog_person_set_pwd(REF, ARG0) :- 
	object_call(REF, setPwd, '.'(ARG0, []), _).

weblog_person_get_idnumber(REF, OUT) :- 
	object_call(REF, getIdnumber, [], OUT).

weblog_person_set_middle_name(REF, ARG0) :- 
	object_call(REF, setMiddleName, '.'(ARG0, []), _).

weblog_person_get_salt(REF, OUT) :- 
	object_call(REF, getSalt, [], OUT).

weblog_person_set_id(REF, ARG0) :- 
	object_call(REF, setId, '.'(ARG0, []), _).

weblog_person_set_user(REF, ARG0) :- 
	object_call(REF, setUser, '.'(ARG0, []), _).

weblog_person_query_one(ARG0, OUT) :- 
	weblog_person_dao(DAO),
	weblog_person_dao_query_one(DAO, ARG0, OUT),
	weblog_person_dao_close(DAO).

weblog_person_query_all(ARG0, OUT) :- 
	weblog_person_dao(DAO),
	weblog_person_dao_query_all(DAO, ARG0, OUT),
	weblog_person_dao_close(DAO).

weblog_person_query_all(ARG0, ARG1, ARG2, OUT) :- 
	weblog_person_dao(DAO),
	weblog_person_dao_query_all(DAO, ARG0, ARG1, ARG2, OUT),
	weblog_person_dao_close(DAO).

weblog_person_retrieve_one(ARG0, OUT) :- 
	weblog_person_dao(DAO),
	weblog_person_dao_retrieve_one(DAO, ARG0, OUT),
	weblog_person_dao_close(DAO).

weblog_person_retrieve_all(OUT) :- 
	weblog_person_dao(DAO),
	weblog_person_dao_retrieve_all(DAO, OUT),
	weblog_person_dao_close(DAO).

weblog_person_retrieve_all(ARG0, ARG1, OUT) :- 
	weblog_person_dao(DAO),
	weblog_person_dao_retrieve_all(DAO, ARG0, ARG1, OUT),
	weblog_person_dao_close(DAO).

