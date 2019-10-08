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

webpro_person_dao(OUT) :- 
	object_new('webpro.model.WebproPersonDao', [], OUT).

webpro_person_dao_equals(REF, ARG0, OUT) :- 
	object_call(REF, equals, '.'(ARG0, []), OUT).

webpro_person_dao_notify_all(REF) :- 
	object_call(REF, notifyAll, [], _).

webpro_person_dao_create(REF, ARG0) :- 
	object_call(REF, create, '.'(ARG0, []), _).

webpro_person_dao_query_one(REF, ARG0, OUT) :- 
	object_call(REF, queryOne, '.'(ARG0, []), OUT).

webpro_person_dao_get_class(REF, OUT) :- 
	object_call(REF, getClass, [], OUT).

webpro_person_dao_hash_code(REF, OUT) :- 
	object_call(REF, hashCode, [], OUT).

webpro_person_dao_notify(REF) :- 
	object_call(REF, notify, [], _).

webpro_person_dao_wait(REF, ARG0, ARG1) :- 
	object_call(REF, wait, '.'(ARG0, '.'(ARG1, [])), _).

webpro_person_dao_wait(REF, ARG0) :- 
	object_call(REF, wait, '.'(ARG0, []), _).

webpro_person_dao_close(REF) :- 
	object_call(REF, close, [], _).

webpro_person_dao_delete(REF, ARG0) :- 
	object_call(REF, delete, '.'(ARG0, []), _).

webpro_person_dao_retrieve_all(REF, OUT) :- 
	object_call(REF, retrieveAll, [], OUT).

webpro_person_dao_retrieve_one(REF, ARG0, OUT) :- 
	object_call(REF, retrieveOne, '.'(ARG0, []), OUT).

webpro_person_dao_query_all(REF, ARG0, OUT) :- 
	object_call(REF, queryAll, '.'(ARG0, []), OUT).

webpro_person_dao_retrieve_all(REF, ARG0, ARG1, OUT) :- 
	object_call(REF, retrieveAll, '.'(ARG0, '.'(ARG1, [])), OUT).

webpro_person_dao_wait(REF) :- 
	object_call(REF, wait, [], _).

webpro_person_dao_update(REF, ARG0) :- 
	object_call(REF, update, '.'(ARG0, []), _).

webpro_person_dao_query_all(REF, ARG0, ARG1, ARG2, OUT) :- 
	object_call(REF, queryAll, '.'(ARG0, '.'(ARG1, '.'(ARG2, []))), OUT).

webpro_person_dao_to_string(REF, OUT) :- 
	object_call(REF, toString, [], OUT).

