#!/usr/bin/bash
java -classpath "$(dirname "$(pwd)")/lib/*" io.github.prolobjectlink.db.prolog.jlog.JLogDatabaseConsole -m
java -classpath "$(dirname "$(pwd)")/lib/*" io.github.prolobjectlink.db.prolog.jlog.JLogDatabaseConsole -z 9110