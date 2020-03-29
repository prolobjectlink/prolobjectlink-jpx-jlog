/*
 * #%L
 * >prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.prolobjectlink.db.prolog.jlog;

import org.prolobjectlink.db.HierarchicalCache;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.prolog.PrologHierarchicalCache;

import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

public class JLogHierarchicalCache extends PrologHierarchicalCache implements HierarchicalCache {

	public JLogHierarchicalCache(PrologProvider provider, Settings settings) {
		super(provider, settings, new JLogContainerFactory(settings));
	}

	public JLogHierarchicalCache(PrologProvider provider, Settings settings, ObjectConverter<PrologTerm> converter) {
		super(provider, settings, converter, new JLogContainerFactory(settings));
	}

}
