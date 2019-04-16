package org.prolobjectlink.db.prolog.jlog;

import org.prolobjectlink.db.prolog.PrologDatabaseEngine;
import org.prolobjectlink.db.prolog.PrologProgrammer;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.jlog.JLogEngine;

public class JLogDatabaseEngine extends JLogEngine implements PrologDatabaseEngine {

	JLogDatabaseEngine() {
		super(new JLogDatabaseProvider());
	}

	JLogDatabaseEngine(PrologProvider provider) {
		super(provider);
	}

	public PrologProgrammer getProgrammer() {
		return new JLogProgrammer(getProvider());
	}

}
