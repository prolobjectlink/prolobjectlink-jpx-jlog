#!/usr/bin/bash
kill $(jps -l | grep org.prolobjectlink.db.prolog.jlog.JLogDatabaseConsole | awk '{print $1}')