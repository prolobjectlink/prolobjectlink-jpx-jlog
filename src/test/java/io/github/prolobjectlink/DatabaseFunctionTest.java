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
package io.github.prolobjectlink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.github.prolobjectlink.db.DatabaseFunction;

public class DatabaseFunctionTest extends BaseTest {

	@Test
	public void testHashCode() {
		DatabaseFunction f = rdb.getSchema().addFunction("pitagoras", "");
		assertEquals(rdb.getSchema().addFunction("pitagoras", "").hashCode(), f.hashCode());
	}

	@Test
	public void testGetName() {
		DatabaseFunction f = rdb.getSchema().addFunction("pitagoras", "");
		assertEquals("pitagoras", f.getName());
	}

	@Test
	public void testGetPath() {
		DatabaseFunction f = rdb.getSchema().addFunction("pitagoras", "");
		assertEquals("db/pdb/relational/test/functions.pl", f.getPath());
	}

	@Test
	public void testGetCode() {

		DatabaseFunction f = rdb.getSchema().addFunction("fn", "");
		f.addParameter("X").addParameter("Y").addParameter("Z").addParameter("R");
		f.addInstructions("R is X*Y*Z");
		assertEquals("fn(X,Y,Z,R) :- \n\tR is X*Y*Z", f.getCode());

	}

	@Test
	public void testGetParameters() {

		DatabaseFunction f = rdb.getSchema().addFunction("fn", "");
		f.addParameter("X").addParameter("Y").addParameter("Z");
		assertEquals(3, f.getParameters().size());

	}

	@Test
	public void testAddParameter() {

		DatabaseFunction f = rdb.getSchema().addFunction("fn", "");
		assertFalse(f.containsParameter("X"));
		assertFalse(f.containsParameter("Y"));
		assertFalse(f.containsParameter("Z"));
		f.addParameter("X");
		f.addParameter("Y");
		f.addParameter("Z");
		assertTrue(f.containsParameter("X"));
		assertTrue(f.containsParameter("Y"));
		assertTrue(f.containsParameter("Z"));

	}

	@Test
	public void testRemoveParameter() {

		DatabaseFunction f = rdb.getSchema().addFunction("fn", "");
		assertFalse(f.containsParameter("X"));
		assertFalse(f.containsParameter("Y"));
		assertFalse(f.containsParameter("Z"));
		f.addParameter("X");
		f.addParameter("Y");
		f.addParameter("Z");
		assertTrue(f.containsParameter("X"));
		assertTrue(f.containsParameter("Y"));
		assertTrue(f.containsParameter("Z"));
		f.removeParameter("X");
		f.removeParameter("Y");
		f.removeParameter("Z");
		assertFalse(f.containsParameter("X"));
		assertFalse(f.containsParameter("Y"));
		assertFalse(f.containsParameter("Z"));

	}

	@Test
	public void testContainsParameter() {

		DatabaseFunction f = rdb.getSchema().addFunction("fn", "");
		assertFalse(f.containsParameter("X"));
		assertFalse(f.containsParameter("Y"));
		assertFalse(f.containsParameter("Z"));
		f.addParameter("X");
		f.addParameter("Y");
		f.addParameter("Z");
		assertTrue(f.containsParameter("X"));
		assertTrue(f.containsParameter("Y"));
		assertTrue(f.containsParameter("Z"));

	}

	@Test
	public void testGetProvider() {
		assertNotNull(rdb.getSchema().addFunction("pitagoras", "").getProvider());
	}

	@Test
	public void testToString() {
		DatabaseFunction f = rdb.getSchema().addFunction("pitagoras", "");
		assertEquals(rdb.getSchema().addFunction("pitagoras", "").toString(), f.toString());
	}

	@Test
	public void testEqualsObject() {
		DatabaseFunction f = rdb.getSchema().addFunction("pitagoras", "");
		assertTrue(f.equals(rdb.getSchema().addFunction("pitagoras", "")));
	}

}
