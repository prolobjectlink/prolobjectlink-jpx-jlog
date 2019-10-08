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

:-consult('../prt/prolog/lang/integer.pl').

:-consult('../prt/prolog/lang/system.pl').

:-consult('../prt/prolog/io/print_stream.pl').

console_print(STRING):- 
	system_out_print(STRING).
	
console_println:- 
	system_out_println.
	
console_println(STRING):- 
	system_out_println(STRING).

system_out_print(STRING):- 
	system_OUT(OUT),
	print_stream_print(OUT, STRING).
	
system_out_println:- 
	system_OUT(OUT),
	print_stream_println(OUT).
	
system_out_println(STRING):- 
	system_OUT(OUT),
	print_stream_println(OUT, STRING).

system_err_print(STRING):- 
	system_ERR(OUT),
	print_stream_print(OUT, STRING).

system_err_println:- 
	system_ERR(OUT),
	print_stream_println(OUT).
	
system_err_println(STRING):- 
	system_ERR(OUT),
	print_stream_println(OUT, STRING).