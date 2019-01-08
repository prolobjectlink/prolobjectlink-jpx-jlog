package org.logicware;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.logicware.db.DatabaseFunction;

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
		assertEquals("dat/relational/test/functions.pl", f.getPath());
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
