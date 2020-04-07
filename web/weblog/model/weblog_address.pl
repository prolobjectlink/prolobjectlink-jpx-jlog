% Copyright (c) 2020 Prolobjectlink Project

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

:-consult('../../../web/weblog/model/weblog_address_dao.pl').

weblog_address(ARG0, ARG1, ARG2, ARG3, ARG4, ARG5, OUT) :- 
	object_new('weblog.model.WeblogAddress', '.'(ARG0, '.'(ARG1, '.'(ARG2, '.'(ARG3, '.'(ARG4, '.'(ARG5, [])))))), OUT).

weblog_address(OUT) :- 
	object_new('weblog.model.WeblogAddress', [], OUT).

weblog_address_get_street(REF, OUT) :- 
	object_call(REF, getStreet, [], OUT).

weblog_address_get_city(REF, OUT) :- 
	object_call(REF, getCity, [], OUT).

weblog_address_get_class(REF, OUT) :- 
	object_call(REF, getClass, [], OUT).

weblog_address_set_state(REF, ARG0) :- 
	object_call(REF, setState, '.'(ARG0, []), _).

weblog_address_set_street(REF, ARG0) :- 
	object_call(REF, setStreet, '.'(ARG0, []), _).

weblog_address_update(REF) :- 
	object_call(REF, update, [], _).

weblog_address_get_id(REF, OUT) :- 
	object_call(REF, getId, [], OUT).

weblog_address_hash_code(REF, OUT) :- 
	object_call(REF, hashCode, [], OUT).

weblog_address_set_country(REF, ARG0) :- 
	object_call(REF, setCountry, '.'(ARG0, []), _).

weblog_address_wait(REF) :- 
	object_call(REF, wait, [], _).

weblog_address_wait(REF, ARG0) :- 
	object_call(REF, wait, '.'(ARG0, []), _).

weblog_address_wait(REF, ARG0, ARG1) :- 
	object_call(REF, wait, '.'(ARG0, '.'(ARG1, [])), _).

weblog_address_notify_all(REF) :- 
	object_call(REF, notifyAll, [], _).

weblog_address_delete(REF) :- 
	object_call(REF, delete, [], _).

weblog_address_notify(REF) :- 
	object_call(REF, notify, [], _).

weblog_address_equals(REF, ARG0, OUT) :- 
	object_call(REF, equals, '.'(ARG0, []), OUT).

weblog_address_get_state(REF, OUT) :- 
	object_call(REF, getState, [], OUT).

weblog_address_get_country(REF, OUT) :- 
	object_call(REF, getCountry, [], OUT).

weblog_address_set_id(REF, ARG0) :- 
	object_call(REF, setId, '.'(ARG0, []), _).

weblog_address_set_zip(REF, ARG0) :- 
	object_call(REF, setZip, '.'(ARG0, []), _).

weblog_address_create(REF) :- 
	object_call(REF, create, [], _).

weblog_address_get_zip(REF, OUT) :- 
	object_call(REF, getZip, [], OUT).

weblog_address_set_city(REF, ARG0) :- 
	object_call(REF, setCity, '.'(ARG0, []), _).

weblog_address_to_string(REF, OUT) :- 
	object_call(REF, toString, [], OUT).

weblog_address_query_one(ARG0, OUT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_query_one(DAO, ARG0, OUT),
	weblog_address_dao_close(DAO).

weblog_address_query_all(ARG0, OUT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_query_all(DAO, ARG0, OUT),
	weblog_address_dao_close(DAO).

weblog_address_query_all(ARG0, ARG1, ARG2, OUT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_query_all(DAO, ARG0, ARG1, ARG2, OUT),
	weblog_address_dao_close(DAO).

weblog_address_retrieve_one(ARG0, OUT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_one(DAO, ARG0, OUT),
	weblog_address_dao_close(DAO).

weblog_address_retrieve_all(OUT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_all(DAO, OUT),
	weblog_address_dao_close(DAO).

weblog_address_retrieve_all(ARG0, ARG1, OUT) :- 
	weblog_address_dao(DAO),
	weblog_address_dao_retrieve_all(DAO, ARG0, ARG1, OUT),
	weblog_address_dao_close(DAO).

