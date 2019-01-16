package org.worklogic.db.prolog;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;
import org.logicware.prolog.PrologVariable;
import org.worklogic.BaseTest;
import org.worklogic.db.prolog.PrologRenamer;
import org.worklogic.domain.geometry.Point;

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
