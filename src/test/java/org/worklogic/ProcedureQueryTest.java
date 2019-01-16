package org.worklogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.worklogic.db.ProcedureQuery;
import org.worklogic.domain.geometry.Point;
import org.worklogic.domain.geometry.Polygon;
import org.worklogic.domain.geometry.Segment;

public class ProcedureQueryTest extends BaseTest {

	private ProcedureQuery query;

	@Test
	@Ignore
	public final void testhasNextAndnext() {

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").execute();

		assertTrue(query.hasNext());
		Object object = query.next();
		assertTrue(object instanceof Object[]);
		Object[] objects = (Object[]) object;
		assertEquals("a", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("b", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("c", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("d", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);

		assertFalse(query.hasNext());

		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1").execute();

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("ab", objects[0]);
		assertEquals(a, objects[1]);
		assertEquals(b, objects[2]);

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("bc", objects[0]);
		assertEquals(b, objects[1]);
		assertEquals(c, objects[2]);

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("ca", objects[0]);
		assertEquals(c, objects[1]);
		assertEquals(a, objects[2]);

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("cd", objects[0]);
		assertEquals(c, objects[1]);
		assertEquals(d, objects[2]);

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("da", objects[0]);
		assertEquals(d, objects[1]);
		assertEquals(a, objects[2]);

		assertFalse(query.hasNext());

		query = storage
				.createProcedureQuery("'" + Polygon.class.getName() + "'", "Id", "Segment0", "Segment1", "Segment2")
				.execute();

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("triangle", objects[0]);
		assertEquals(ab, objects[1]);
		assertEquals(bc, objects[2]);
		assertEquals(ca, objects[3]);

		assertFalse(query.hasNext());

	}

	@Test
	@Ignore
	public final void testSetAndGetMaxSolution() {

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").setMaxSolution(2)
				.execute();
		List<Object[]> expectedObjectsArrays = Arrays.asList(new Object[] { "a", 3, 14 }, new Object[] { "b", 3, 14 });
		List<Object> actualObjectsArrays = query.getSolutions();

		assertEquals(2, actualObjectsArrays.size());
		assertEquals(2, query.getMaxSolution());

		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedObjectsArrays.get(i), objects);
		}

		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1")
				.setMaxSolution(3).execute();
		expectedObjectsArrays = Arrays.asList(new Object[] { "ab", a, b }, new Object[] { "bc", b, c },
				new Object[] { "ca", c, a }, new Object[] { "cd", c, d });
		actualObjectsArrays = query.getSolutions();

		assertEquals(3, actualObjectsArrays.size());
		assertEquals(3, query.getMaxSolution());

		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedObjectsArrays.get(i), objects);
		}

	}

	@Test
	@Ignore
	public final void testSetAndGetFirstResult() {

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").setFirstSolution(0)
				.execute();
		List<Object[]> expectedObjectsArrays = Arrays.asList(new Object[] { "a", 3, 14 }, new Object[] { "b", 3, 14 },
				new Object[] { "c", 3, 14 }, new Object[] { "d", 3, 14 });
		List<Object> actualObjectsArrays = query.getSolutions();

		assertEquals(0, query.getFirstSolution());
		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedObjectsArrays.get(i), objects);
		}

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").setFirstSolution(1)
				.execute();
		expectedObjectsArrays = Arrays.asList(new Object[] { "b", 3, 14 }, new Object[] { "c", 3, 14 },
				new Object[] { "d", 3, 14 });
		actualObjectsArrays = query.getSolutions();

		assertEquals(1, query.getFirstSolution());
		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedObjectsArrays.get(i), objects);
		}

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").setFirstSolution(2)
				.execute();
		expectedObjectsArrays = Arrays.asList(new Object[] { "c", 3, 14 }, new Object[] { "d", 3, 14 });
		actualObjectsArrays = query.getSolutions();

		assertEquals(2, query.getFirstSolution());
		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedObjectsArrays.get(i), objects);
		}

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").setFirstSolution(3)
				.execute();
		Object[] objectArray = new Object[] { "d", 3, 14 };
		actualObjectsArrays = query.getSolutions();

		assertEquals(3, query.getFirstSolution());
		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(objectArray, objects);
		}

	}

	@Test
	@Ignore
	public final void testGetSolution() {

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").execute();
		Object solution = query.getSolution();
		assertTrue(solution instanceof Object[]);
		Object[] objects = (Object[]) solution;
		assertEquals("a", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);

		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1").execute();
		solution = query.getSolution();
		assertTrue(solution instanceof Object[]);
		objects = (Object[]) solution;
		assertEquals("ab", objects[0]);
		assertEquals(a, objects[1]);
		assertEquals(b, objects[2]);

		query = storage
				.createProcedureQuery("'" + Polygon.class.getName() + "'", "Id", "Segment0", "Segment1", "Segment2")
				.execute();
		solution = query.getSolution();
		assertTrue(solution instanceof Object[]);
		objects = (Object[]) solution;
		assertEquals("triangle", objects[0]);
		assertEquals(ab, objects[1]);
		assertEquals(bc, objects[2]);
		assertEquals(ca, objects[3]);

	}

	@Test
	@Ignore
	public final void testGetSolutions() {

		query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y").execute();
		List<Object[]> expectedObjectsArrays = Arrays.asList(new Object[] { "a", 3, 14 }, new Object[] { "b", 3, 14 },
				new Object[] { "c", 3, 14 }, new Object[] { "d", 3, 14 });
		List<Object> actualObjectsArrays = query.getSolutions();

		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedObjectsArrays.get(i), objects);
		}

		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1").execute();
		expectedObjectsArrays = Arrays.asList(new Object[] { "ab", a, b }, new Object[] { "bc", b, c },
				new Object[] { "ca", c, a }, new Object[] { "cd", c, d }, new Object[] { "da", d, a });
		actualObjectsArrays = query.getSolutions();

		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedObjectsArrays.get(i), objects);
		}

		query = storage
				.createProcedureQuery("'" + Polygon.class.getName() + "'", "Id", "Segment0", "Segment1", "Segment2")
				.execute();
		Object[] expectedArraysObjects = new Object[] { "triangle", ab, bc, ca };
		actualObjectsArrays = query.getSolutions();

		for (int i = 0; i < actualObjectsArrays.size(); i++) {
			Object object = actualObjectsArrays.get(i);
			assertTrue(object instanceof Object[]);
			Object[] objects = (Object[]) object;
			Assert.assertArrayEquals(expectedArraysObjects, objects);
		}

	}

	@Test
	@Ignore
	public final void testSetAndGetArgumentValueByName() {

		// no arguments binding
		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1");
		query.execute();

		assertEquals("ab", query.getArgumentValue("Ids"));
		assertEquals(a, query.getArgumentValue("Point0"));
		assertEquals(b, query.getArgumentValue("Point1"));

		// all arguments binding
		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1");

		query.setArgumentValue("Ids", "ca");
		query.setArgumentValue("Point0", c);
		query.setArgumentValue("Point1", a);

		query.execute();

		assertEquals("ca", query.getArgumentValue("Ids"));
		assertEquals(c, query.getArgumentValue("Point0"));
		assertEquals(a, query.getArgumentValue("Point1"));

		// partial arguments binding
		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1");
		query.setArgumentValue("Point1", a);
		query.execute();

		assertEquals("ca", query.getArgumentValue("Ids"));
		assertEquals(c, query.getArgumentValue("Point0"));
		assertEquals(a, query.getArgumentValue("Point1"));

	}

	@Test
	@Ignore
	public final void testSetAndGetArgumentValueByPosition() {

		// no arguments binding
		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1");
		query.execute();

		assertEquals("ab", query.getArgumentValue(0));
		assertEquals(a, query.getArgumentValue(1));
		assertEquals(b, query.getArgumentValue(2));

		// all arguments binding
		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1");
		query.setArgumentValue(0, "ca");
		query.setArgumentValue(1, c);
		query.setArgumentValue(2, a);
		query.execute();

		assertEquals("ca", query.getArgumentValue(0));
		assertEquals(c, query.getArgumentValue(1));
		assertEquals(a, query.getArgumentValue(2));

		// partial arguments binding
		query = storage.createProcedureQuery("'" + Segment.class.getName() + "'", "Ids", "Point0", "Point1");
		query.setArgumentValue(2, a);
		query.execute();

		assertEquals("ca", query.getArgumentValue(0));
		assertEquals(c, query.getArgumentValue(1));
		assertEquals(a, query.getArgumentValue(2));

	}

}
