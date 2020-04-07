#!/usr/bin/bash
kill $(jps -l | grep io.github.prolobjectlink.db.prolog.jlog.JLogDatabaseConsole | awk '{print $1}')