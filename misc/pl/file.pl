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

:-consult('../../obj/prolobject.pl').

file(ARG0, OUT) :- 
	object_new('org.prolobjectlink.web.io.ServerFile', '.'(ARG0, []), OUT).

file_exists(REF, OUT) :- 
	object_call(REF, exists, [], OUT).

file_get_web_directory(REF, OUT) :- 
	object_call(REF, getWebDirectory, [], OUT).

file_get_root_directory(REF, OUT) :- 
	object_call(REF, getRootDirectory, [], OUT).

file_is_absolute(REF, OUT) :- 
	object_call(REF, isAbsolute, [], OUT).

file_set_executable(REF, ARG0, OUT) :- 
	object_call(REF, setExecutable, '.'(ARG0, []), OUT).

file_set_writable(REF, ARG0, OUT) :- 
	object_call(REF, setWritable, '.'(ARG0, []), OUT).

file_set_executable(REF, ARG0, ARG1, OUT) :- 
	object_call(REF, setExecutable, '.'(ARG0, '.'(ARG1, [])), OUT).

file_set_writable(REF, ARG0, ARG1, OUT) :- 
	object_call(REF, setWritable, '.'(ARG0, '.'(ARG1, [])), OUT).

file_compare_to(REF, ARG0, OUT) :- 
	object_call(REF, compareTo, '.'(ARG0, []), OUT).

file_get_parent_file(REF, OUT) :- 
	object_call(REF, getParentFile, [], OUT).

file_is_hidden(REF, OUT) :- 
	object_call(REF, isHidden, [], OUT).

file_mkdirs(REF, OUT) :- 
	object_call(REF, mkdirs, [], OUT).

file_get_current_path(REF, OUT) :- 
	object_call(REF, getCurrentPath, [], OUT).

file_get_java_home(REF, OUT) :- 
	object_call(REF, getJavaHome, [], OUT).

file_get_path_ext(REF, OUT) :- 
	object_call(REF, getPathExt, [], OUT).

file_to_string(REF, OUT) :- 
	object_call(REF, toString, [], OUT).

file_run_on_windows(REF, OUT) :- 
	object_call(REF, runOnWindows, [], OUT).

file_get_d_b_directory(REF, OUT) :- 
	object_call(REF, getDBDirectory, [], OUT).

file_run_on_linux(REF, OUT) :- 
	object_call(REF, runOnLinux, [], OUT).

file_to_u_r_i(REF, OUT) :- 
	object_call(REF, toURI, [], OUT).

file_rename_to(REF, ARG0, OUT) :- 
	object_call(REF, renameTo, '.'(ARG0, []), OUT).

file_run_on_os_x(REF, OUT) :- 
	object_call(REF, runOnOsX, [], OUT).

file_equals(REF, ARG0, OUT) :- 
	object_call(REF, equals, '.'(ARG0, []), OUT).

file_get_class(REF, OUT) :- 
	object_call(REF, getClass, [], OUT).

file_get_arch(REF, OUT) :- 
	object_call(REF, getArch, [], OUT).

file_run(REF, ARG0, OUT) :- 
	object_call(REF, run, '.'(ARG0, []), OUT).

file_get_os_name(REF, OUT) :- 
	object_call(REF, getOsName, [], OUT).

file_get_user_home(REF, OUT) :- 
	object_call(REF, getUserHome, [], OUT).

file_create_temp_file(REF, ARG0, ARG1, ARG2, OUT) :- 
	object_call(REF, createTempFile, '.'(ARG0, '.'(ARG1, '.'(ARG2, []))), OUT).

file_create_temp_file(REF, ARG0, ARG1, OUT) :- 
	object_call(REF, createTempFile, '.'(ARG0, '.'(ARG1, [])), OUT).

file_get_user_dir(REF, OUT) :- 
	object_call(REF, getUserDir, [], OUT).

file_can_read(REF, OUT) :- 
	object_call(REF, canRead, [], OUT).

file_get_bin_directory(REF, OUT) :- 
	object_call(REF, getBinDirectory, [], OUT).

file_get_user_name(REF, OUT) :- 
	object_call(REF, getUserName, [], OUT).

file_list(REF, ARG0, OUT) :- 
	object_call(REF, list, '.'(ARG0, []), OUT).

file_copy(REF, ARG0, ARG1, OUT) :- 
	object_call(REF, copy, '.'(ARG0, '.'(ARG1, [])), OUT).

file_list(REF, OUT) :- 
	object_call(REF, list, [], OUT).

file_get_java_version(REF, OUT) :- 
	object_call(REF, getJavaVersion, [], OUT).

file_delete(REF, OUT) :- 
	object_call(REF, delete, [], OUT).

file_length(REF, OUT) :- 
	object_call(REF, length, [], OUT).

file_get_java_vendor(REF, OUT) :- 
	object_call(REF, getJavaVendor, [], OUT).

file_set_readable(REF, ARG0, OUT) :- 
	object_call(REF, setReadable, '.'(ARG0, []), OUT).

file_can_execute(REF, OUT) :- 
	object_call(REF, canExecute, [], OUT).

file_set_readable(REF, ARG0, ARG1, OUT) :- 
	object_call(REF, setReadable, '.'(ARG0, '.'(ARG1, [])), OUT).

file_get_prt_directory(REF, OUT) :- 
	object_call(REF, getPrtDirectory, [], OUT).

file_get_java_name(REF, OUT) :- 
	object_call(REF, getJavaName, [], OUT).

file_get_work_dir(REF, OUT) :- 
	object_call(REF, getWorkDir, [], OUT).

file_is_file(REF, OUT) :- 
	object_call(REF, isFile, [], OUT).

file_can_write(REF, OUT) :- 
	object_call(REF, canWrite, [], OUT).

file_list_roots(REF, OUT) :- 
	object_call(REF, listRoots, [], OUT).

file_set_read_only(REF, OUT) :- 
	object_call(REF, setReadOnly, [], OUT).

file_get_misc_directory(REF, OUT) :- 
	object_call(REF, getMiscDirectory, [], OUT).

file_check_j_d_k_instalation(REF) :- 
	object_call(REF, checkJDKInstalation, [], _).

file_create_new_file(REF, OUT) :- 
	object_call(REF, createNewFile, [], OUT).

file_get_file_path(REF, OUT) :- 
	object_call(REF, getFilePath, [], OUT).

file_get_usable_space(REF, OUT) :- 
	object_call(REF, getUsableSpace, [], OUT).

file_get_total_space(REF, OUT) :- 
	object_call(REF, getTotalSpace, [], OUT).

file_mkdir(REF, OUT) :- 
	object_call(REF, mkdir, [], OUT).

file_get_lib_directory(REF, OUT) :- 
	object_call(REF, getLibDirectory, [], OUT).

file_wait(REF, ARG0, ARG1) :- 
	object_call(REF, wait, '.'(ARG0, '.'(ARG1, [])), _).

file_get_runtime(REF, OUT) :- 
	object_call(REF, getRuntime, [], OUT).

file_wait(REF, ARG0) :- 
	object_call(REF, wait, '.'(ARG0, []), _).

file_get_canonical_path(REF, OUT) :- 
	object_call(REF, getCanonicalPath, [], OUT).

file_wait(REF) :- 
	object_call(REF, wait, [], _).

file_notify_all(REF) :- 
	object_call(REF, notifyAll, [], _).

file_to_u_r_l(REF, OUT) :- 
	object_call(REF, toURL, [], OUT).

file_get_path(REF, OUT) :- 
	object_call(REF, getPath, [], OUT).

file_to_path(REF, OUT) :- 
	object_call(REF, toPath, [], OUT).

file_get_canonical_file(REF, OUT) :- 
	object_call(REF, getCanonicalFile, [], OUT).

file_get_name(REF, OUT) :- 
	object_call(REF, getName, [], OUT).

file_get_free_space(REF, OUT) :- 
	object_call(REF, getFreeSpace, [], OUT).

file_get_absolute_file(REF, OUT) :- 
	object_call(REF, getAbsoluteFile, [], OUT).

file_get_absolute_path(REF, OUT) :- 
	object_call(REF, getAbsolutePath, [], OUT).

file_list_files(REF, ARG0, OUT) :- 
	object_call(REF, listFiles, '.'(ARG0, []), OUT).

file_list_files(REF, ARG0, OUT) :- 
	object_call(REF, listFiles, '.'(ARG0, []), OUT).

file_delete_on_exit(REF) :- 
	object_call(REF, deleteOnExit, [], _).

file_list_files(REF, OUT) :- 
	object_call(REF, listFiles, [], OUT).

file_set_last_modified(REF, ARG0, OUT) :- 
	object_call(REF, setLastModified, '.'(ARG0, []), OUT).

file_get_path_separator(REF, OUT) :- 
	object_call(REF, getPathSeparator, [], OUT).

file_last_modified(REF, OUT) :- 
	object_call(REF, lastModified, [], OUT).

file_get_class_path(REF, OUT) :- 
	object_call(REF, getClassPath, [], OUT).

file_get_parent(REF, OUT) :- 
	object_call(REF, getParent, [], OUT).

file_notify(REF) :- 
	object_call(REF, notify, [], _).

file_is_directory(REF, OUT) :- 
	object_call(REF, isDirectory, [], OUT).

file_hash_code(REF, OUT) :- 
	object_call(REF, hashCode, [], OUT).

