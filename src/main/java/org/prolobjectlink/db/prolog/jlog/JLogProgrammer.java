/*-
 * #%L
 * prolobjectlink-jpi-jlog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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

import java.io.PrintWriter;

import org.prolobjectlink.db.prolog.AbstractProgrammer;
import org.prolobjectlink.db.prolog.PrologProgrammer;
import org.prolobjectlink.prolog.PrologProvider;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public final class JLogProgrammer extends AbstractProgrammer implements PrologProgrammer {

	public JLogProgrammer(PrologProvider provider) {
		super(provider);
	}

	public void codingInclusion(PrintWriter programmer, String jarEntryName) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < jarEntryName.lastIndexOf('/'); i++) {
			if (jarEntryName.charAt(i) == '/') {
				b.append("../");
			}
		}
		b.append("../../obj/prolobject.pl");
		programmer.println(":-" + provider.prologInclude("" + b + "") + ".");
		programmer.println();
	}

}