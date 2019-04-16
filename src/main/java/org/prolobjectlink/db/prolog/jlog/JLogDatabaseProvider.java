package org.prolobjectlink.db.prolog.jlog;

import org.prolobjectlink.db.prolog.PrologDatabaseEngine;
import org.prolobjectlink.db.prolog.PrologDatabaseProvider;
import org.prolobjectlink.prolog.jlog.JLog;

public class JLogDatabaseProvider extends JLog implements PrologDatabaseProvider {

	public PrologDatabaseEngine newEngine() {
		return new JLogDatabaseEngine(this);
	}

	public PrologDatabaseEngine newEngine(String path) {
		PrologDatabaseEngine engine = newEngine();
		engine.consult(path);
		return engine;
	}

}
