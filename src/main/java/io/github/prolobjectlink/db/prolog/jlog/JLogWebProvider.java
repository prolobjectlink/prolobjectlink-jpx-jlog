/*-
 * #%L
 * prolobjectlink-jpx-jpl-swi
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package io.github.prolobjectlink.db.prolog.jlog;

import io.github.prolobjectlink.prolog.jlog.JLog;
import io.github.prolobjectlink.web.prolog.PrologWebEngine;
import io.github.prolobjectlink.web.prolog.PrologWebProvider;

public class JLogWebProvider extends JLog implements PrologWebProvider {

	public PrologWebEngine newEngine() {
		return new JLogWebEngine(this);
	}

	public PrologWebEngine newEngine(String path) {
		PrologWebEngine engine = newEngine();
		engine.consult(path);
		return engine;
	}

}
