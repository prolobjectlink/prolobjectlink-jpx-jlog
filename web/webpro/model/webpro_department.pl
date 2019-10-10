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

:-consult('../../../web/webpro/model/webpro_department_dao.pl').

webpro_department(OUT) :- 
	object_new('webpro.model.WebproDepartment', [], OUT).

webpro_department_set_id(REF, ARG0) :- 
	object_call(REF, setId, '.'(ARG0, []), _).

webpro_department_create(REF) :- 
	object_call(REF, create, [], _).

webpro_department_equals(REF, ARG0, OUT) :- 
	object_call(REF, equals, '.'(ARG0, []), OUT).

webpro_department_wait(REF, ARG0) :- 
	object_call(REF, wait, '.'(ARG0, []), _).

webpro_department_wait(REF, ARG0, ARG1) :- 
	object_call(REF, wait, '.'(ARG0, '.'(ARG1, [])), _).

webpro_department_update(REF) :- 
	object_call(REF, update, [], _).

webpro_department_notify_all(REF) :- 
	object_call(REF, notifyAll, [], _).

webpro_department_wait(REF) :- 
	object_call(REF, wait, [], _).

webpro_department_hash_code(REF, OUT) :- 
	object_call(REF, hashCode, [], OUT).

webpro_department_get_class(REF, OUT) :- 
	object_call(REF, getClass, [], OUT).

webpro_department_set_name(REF, ARG0) :- 
	object_call(REF, setName, '.'(ARG0, []), _).

webpro_department_delete(REF) :- 
	object_call(REF, delete, [], _).

webpro_department_get_name(REF, OUT) :- 
	object_call(REF, getName, [], OUT).

webpro_department_notify(REF) :- 
	object_call(REF, notify, [], _).

webpro_department_to_string(REF, OUT) :- 
	object_call(REF, toString, [], OUT).

webpro_department_get_id(REF, OUT) :- 
	object_call(REF, getId, [], OUT).

webpro_department_query_one(ARG0, OUT) :- 
	webpro_department_dao(DAO),
	webpro_department_dao_query_one(DAO, ARG0, OUT),
	webpro_department_dao_close(DAO).

webpro_department_query_all(ARG0, OUT) :- 
	webpro_department_dao(DAO),
	webpro_department_dao_query_all(DAO, ARG0, OUT),
	webpro_department_dao_close(DAO).

webpro_department_query_all(ARG0, ARG1, ARG2, OUT) :- 
	webpro_department_dao(DAO),
	webpro_department_dao_query_all(DAO, ARG0, ARG1, ARG2, OUT),
	webpro_department_dao_close(DAO).

webpro_department_retrieve_one(ARG0, OUT) :- 
	webpro_department_dao(DAO),
	webpro_department_dao_retrieve_one(DAO, ARG0, OUT),
	webpro_department_dao_close(DAO).

webpro_department_retrieve_all(OUT) :- 
	webpro_department_dao(DAO),
	webpro_department_dao_retrieve_all(DAO, OUT),
	webpro_department_dao_close(DAO).

webpro_department_retrieve_all(ARG0, ARG1, OUT) :- 
	webpro_department_dao(DAO),
	webpro_department_dao_retrieve_all(DAO, ARG0, ARG1, OUT),
	webpro_department_dao_close(DAO).

