@echo off
FOR /F "tokens=1,2 delims= " %%G IN ('jps -l') DO IF %%H==org.prolobjectlink.db.prolog.jlog.JLogDatabaseConsole taskkill /F /PID %%G