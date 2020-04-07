/*-
 * #%L
 * prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package io.github.prolobjectlink.db.prolog;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;

import io.github.prolobjectlink.BaseTest;
import io.github.prolobjectlink.db.prolog.PrologRenamer;
import io.github.prolobjectlink.domain.geometry.Point;
import io.github.prolobjectlink.prolog.PrologVariable;

public class RenamerTest extends BaseTest {

	private PrologRenamer r = new PrologRenamer(provider);
	private Field[] expected = Point.class.getDeclaredFields();
	private PrologVariable[] names = new PrologVariable[expected.length];

	@Test
	public final void testRename() {

		// from field to prolog variable
		for (int i = 0; i < expected.length; i++) {
			names[i] = r.toVariable(expected[i]);
		}

		// from variable to filed
		Field[] actual = Point.class.getDeclaredFields();
		for (int i = 0; i < names.length; i++) {
			actual[i] = r.toField(names[i]);
		}

		assertArrayEquals(expected, actual);

	}

	@Test
	public final void testGetVariableMap() {
		assertNotNull(r.getVariableMap());
	}

	@Test
	public final void testGetProvider() {
		assertNotNull(r.getProvider());
	}

}
