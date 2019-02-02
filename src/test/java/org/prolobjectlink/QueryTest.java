package org.prolobjectlink;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.domain.geometry.Point;
import org.prolobjectlink.domain.geometry.Polygon;
import org.prolobjectlink.domain.geometry.Segment;

public class QueryTest extends BaseTest {

	private Query query;
	private Object solution;
	private List<Object> solutions;

	@Test
	public final void testSetAndGetFirstResult() {

		storage.getTransaction().begin();

		List<Point> points = Arrays.asList(a, b, c, d);
		Query query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").setFirstSolution(0);
		solutions = query.getSolutions();
		assertEquals(points, createList(solutions));
		assertEquals(4, solutions.size());
		assertEquals(0, query.getFirstSolution());

		points = Arrays.asList(b, c, d);
		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").setFirstSolution(1);
		solutions = query.getSolutions();
		assertEquals(points, createList(solutions));
		assertEquals(3, solutions.size());
		assertEquals(1, query.getFirstSolution());

		points = Arrays.asList(c, d);
		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").setFirstSolution(2);
		solutions = query.getSolutions();
		assertEquals(points, createList(solutions));
		assertEquals(2, solutions.size());
		assertEquals(2, query.getFirstSolution());

		points = Arrays.asList(d);
		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").setFirstSolution(3);
		solutions = query.getSolutions();
		assertEquals(points, createList(solutions));
		assertEquals(1, solutions.size());
		assertEquals(3, query.getFirstSolution());

		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").setFirstSolution(4);
		assertEquals(4, query.getFirstSolution());

		storage.getTransaction().close();

	}

	@Test
	public final void testSetAndGetMaxSolution() {

		storage.getTransaction().begin();

		List<Point> points = Arrays.asList(a, b);

		Query query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").setMaxSolution(2);
		solutions = query.getSolutions();
		assertEquals(2, solutions.size());
		assertEquals(2, query.getMaxSolution());
		assertEquals(points, createList(solutions));
		assertEquals(2, query.getMaxSolution());
		assertEquals(2, query.getSolutions().size());

		storage.getTransaction().close();
	}

	@Test
	public final void testGetSolution() {

		storage.getTransaction().begin();

		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)");
		solution = query.getSolution();
		if (solution instanceof Object[]) {
			Object[] objects = (Object[]) solution;
			if (objects.length > 0) {
				Object object = objects[0];
				if (object instanceof Point) {
					Point point = (Point) object;
					assertEquals(a, point);
				}
			}
		}

		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14");
		solution = query.getSolution();
		if (solution instanceof Object[]) {
			Object[] objects = (Object[]) solution;
			if (objects.length > 0) {
				Object object = objects[0];
				if (object instanceof Point) {
					Point point = (Point) object;
					assertEquals(a, point);
				}
			}
		}

		query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		solution = query.getSolution();
		if (solution instanceof Object[]) {
			Object[] objects = (Object[]) solution;
			if (objects.length > 0) {
				Object object = objects[0];
				if (object instanceof Segment) {
					Segment segment = (Segment) object;
					assertEquals(ab, segment);
				}
			}
		}

		query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");
		solution = query.getSolution();
		if (solution instanceof Object[]) {
			Object[] objects = (Object[]) solution;
			if (objects.length > 0) {
				Object object = objects[0];
				if (object instanceof Segment) {
					Segment segment = (Segment) object;
					assertEquals(ab, segment);
				}
			}
		}

		query = storage.createQuery(
				"'" + Point.class.getName() + "'(Idp, X, Y), '" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		solution = query.getSolution();
		if (solution instanceof Object[]) {
			Object[] objects = (Object[]) solution;
			if (objects.length > 1) {
				Object object0 = objects[0];
				Object object1 = objects[1];
				if (object0 instanceof Point) {
					Point point = (Point) object0;
					assertEquals(a, point);
				}
				if (object1 instanceof Segment) {
					Segment segment = (Segment) object1;
					assertEquals(ab, segment);
				}
			}
		}

		storage.getTransaction().close();

	}

	@Test
	public final void testGetSolutions() {

		storage.getTransaction().begin();

		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)");
		solutions = query.getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		// point query restricted to specifics x and y points
		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14");
		solutions = query.getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		solutions = query.getSolutions();
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(solutions));

		// segment query restricted to specifics points initial and finals
		query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");
		solutions = query.getSolutions();
		assertEquals(Arrays.asList(ab), createList(solutions));

		// predicate projection
		List<Point> points = new ArrayList<Point>();
		List<Segment> segments = new ArrayList<Segment>();
		query = storage.createQuery(
				"'" + Point.class.getName() + "'(Idp, X, Y), '" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		solutions = query.getSolutions();
		for (Object solution : solutions) {
			if (solution instanceof Object[]) {
				Object[] objects = (Object[]) solution;
				if (objects.length > 1) {
					Object object0 = objects[0];
					Object object1 = objects[1];
					if (object0 instanceof Point) {
						Point point = (Point) object0;
						points.add(point);
					}
					if (object1 instanceof Segment) {
						Segment segment = (Segment) object1;
						segments.add(segment);
					}
				}
			}
		}

		storage.getTransaction().close();

		// all combination created by the Projection or Cartesian Product
		assertEquals(Arrays.asList(a, a, a, a, a, b, b, b, b, b, c, c, c, c, c, d, d, d, d, d), points);
		assertEquals(Arrays.asList(ab, bc, ca, cd, da, ab, bc, ca, cd, da, ab, bc, ca, cd, da, ab, bc, ca, cd, da),
				segments);

	}

	@Test
	public final void testOrderAscending() {

		storage.getTransaction().begin();

		List<Point> points = Arrays.asList(a, b, c, d);
		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").orderAscending();
		solutions = query.getSolutions();
		assertEquals(points, createList(solutions));

		List<Segment> segments = Arrays.asList(ab, bc, ca, cd, da);
		query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").orderAscending();
		solutions = query.getSolutions();
		assertEquals(segments, createList(solutions));

		storage.getTransaction().close();

	}

	@Test
	public final void testOrderDescending() {

		storage.getTransaction().begin();

		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").orderDescending();
		solutions = query.getSolutions();
		assertEquals(Arrays.asList(d, c, b, a), createList(solutions));

		List<Segment> segments = Arrays.asList(da, cd, ca, bc, ab);
		query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").orderDescending();
		solutions = query.getSolutions();
		assertEquals(segments, createList(solutions));

		storage.getTransaction().close();

	}

	@Test
	public final void testOrderBy() {

		storage.getTransaction().begin();

		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").orderBy(new Comparator<Object>() {

			public int compare(Object o1, Object o2) {

				if (o1 instanceof Object[] && o2 instanceof Object[]) {

					Object[] objects1 = (Object[]) o1;
					Object[] objects2 = (Object[]) o2;

					// comparison by length
					if (objects1.length < objects2.length) {
						return -1;
					} else if (objects1.length > objects2.length) {
						return 1;
					}

					for (int i = 0; i < objects1.length; i++) {

						Object object1 = objects1[i];
						Object object2 = objects2[i];

						if (object1 instanceof Object[] && object2 instanceof Object[]) {
							return compare(object1, object2);
						} else if (object1.hashCode() < object2.hashCode()) {
							return -1;
						} else if (object1.hashCode() > object2.hashCode()) {
							return 1;
						}

					}

				}

				return 0;
			}
		});

		solutions = query.getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").orderBy(new Comparator<Object>() {

			public int compare(Object o1, Object o2) {

				if (o1 instanceof Object[] && o2 instanceof Object[]) {

					Object[] objects1 = (Object[]) o1;
					Object[] objects2 = (Object[]) o2;

					// comparison by length
					if (objects1.length < objects2.length) {
						return 1;
					} else if (objects1.length > objects2.length) {
						return -1;
					}

					for (int i = 0; i < objects1.length; i++) {

						Object object1 = objects1[i];
						Object object2 = objects2[i];

						if (object1 instanceof Object[] && object2 instanceof Object[]) {
							return compare(object1, object2);
						} else if (object1.hashCode() < object2.hashCode()) {
							return 1;
						} else if (object1.hashCode() > object2.hashCode()) {
							return -1;
						}

					}

				}

				return 0;
			}
		});

		solutions = query.getSolutions();
		assertEquals(Arrays.asList(d, c, b, a), createList(solutions));

		storage.getTransaction().close();

	}

	@Test
	public final void testDescend() {

		storage.getTransaction().begin();

		List<String> actual = new ArrayList<String>();
		List<String> expected = Arrays.asList(a.getIdp(), b.getIdp(), c.getIdp(), d.getIdp());
		query = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").descend("idp");
		solutions = query.getSolutions();
		for (Object object : solutions) {
			if (object instanceof Object[]) {
				Object[] array = (Object[]) object;
				for (Object objectInArray : array) {
					if (objectInArray instanceof String) {
						String String = (String) objectInArray;
						actual.add(String);
					}
				}
			}
		}
		assertEquals(expected, actual);

		actual = new ArrayList<String>();
		expected = Arrays.asList(a.getIdp(), b.getIdp(), c.getIdp(), c.getIdp(), d.getIdp());
		query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").descend("point0")
				.descend("idp");
		solutions = query.getSolutions();
		for (Object object : solutions) {
			if (object instanceof Object[]) {
				Object[] array = (Object[]) object;
				for (Object objectInArray : array) {
					if (objectInArray instanceof String) {
						String String = (String) objectInArray;
						actual.add(String);
					}
				}
			}
		}
		assertEquals(expected, actual);

		storage.getTransaction().close();

	}

	@Test
	public final void testhasNextAndnext() {

		storage.getTransaction().begin();

		Query query = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");

		assertTrue(query.hasNext());
		assertArrayEquals(new Object[] { ab }, (Object[]) query.next());
		assertTrue(query.hasNext());
		assertArrayEquals(new Object[] { bc }, (Object[]) query.next());
		assertTrue(query.hasNext());
		assertArrayEquals(new Object[] { ca }, (Object[]) query.next());
		assertTrue(query.hasNext());
		assertArrayEquals(new Object[] { cd }, (Object[]) query.next());
		assertTrue(query.hasNext());
		assertArrayEquals(new Object[] { da }, (Object[]) query.next());

		assertFalse(query.hasNext());

		storage.getTransaction().close();

	}

	@Test
	public final void testMax() {

		storage.getTransaction().begin();

		Query query1 = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)");
		assertArrayEquals(new Object[] { d }, (Object[]) query1.max());

		Query query4 = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		assertArrayEquals(new Object[] { da }, (Object[]) query4.max());

		storage.getTransaction().close();

	}

	@Test
	public final void testMaxComparatorOfT() {

		storage.getTransaction().begin();

		Query query1 = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)");
		assertArrayEquals(new Object[] { d }, (Object[]) query1.max(new Comparator<Object>() {

			public int compare(Object o1, Object o2) {

				if (o1 instanceof Object[] && o2 instanceof Object[]) {

					Object[] objects1 = (Object[]) o1;
					Object[] objects2 = (Object[]) o2;

					// comparison by length
					if (objects1.length < objects2.length) {
						return -1;
					} else if (objects1.length > objects2.length) {
						return 1;
					}

					for (int i = 0; i < objects1.length; i++) {

						Object object1 = objects1[i];
						Object object2 = objects2[i];

						if (object1 instanceof Object[] && object2 instanceof Object[]) {
							return compare(object1, object2);
						} else if (object1.hashCode() < object2.hashCode()) {
							return -1;
						} else if (object1.hashCode() > object2.hashCode()) {
							return 1;
						}

					}

				}

				return 0;
			}
		}));

		Query query4 = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		assertArrayEquals(new Object[] { da }, (Object[]) query4.max(new Comparator<Object>() {

			public int compare(Object o1, Object o2) {

				if (o1 instanceof Object[] && o2 instanceof Object[]) {

					Object[] objects1 = (Object[]) o1;
					Object[] objects2 = (Object[]) o2;

					// comparison by length
					if (objects1.length < objects2.length) {
						return -1;
					} else if (objects1.length > objects2.length) {
						return 1;
					}

					for (int i = 0; i < objects1.length; i++) {

						Object object1 = objects1[i];
						Object object2 = objects2[i];

						if (object1 instanceof Object[] && object2 instanceof Object[]) {
							return compare(object1, object2);
						} else if (object1.hashCode() < object2.hashCode()) {
							return -1;
						} else if (object1.hashCode() > object2.hashCode()) {
							return 1;
						}

					}

				}

				return 0;
			}
		}));

		storage.getTransaction().close();

	}

	@Test
	public final void testMin() {

		storage.getTransaction().begin();

		Query query2 = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)");
		assertArrayEquals(new Object[] { a }, (Object[]) query2.min());

		Query query3 = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		assertArrayEquals(new Object[] { ab }, (Object[]) query3.min());

		storage.getTransaction().close();

	}

	@Test
	public final void testMinComparatorOfT() {

		storage.getTransaction().begin();

		Query query2 = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)");
		assertArrayEquals(new Object[] { a }, (Object[]) query2.min(new Comparator<Object>() {

			public int compare(Object o1, Object o2) {

				if (o1 instanceof Object[] && o2 instanceof Object[]) {

					Object[] objects1 = (Object[]) o1;
					Object[] objects2 = (Object[]) o2;

					// comparison by length
					if (objects1.length < objects2.length) {
						return -1;
					} else if (objects1.length > objects2.length) {
						return 1;
					}

					for (int i = 0; i < objects1.length; i++) {

						Object object1 = objects1[i];
						Object object2 = objects2[i];

						if (object1 instanceof Object[] && object2 instanceof Object[]) {
							return compare(object1, object2);
						} else if (object1.hashCode() < object2.hashCode()) {
							return -1;
						} else if (object1.hashCode() > object2.hashCode()) {
							return 1;
						}

					}

				}

				return 0;
			}
		}));

		Query query3 = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		assertArrayEquals(new Object[] { ab }, (Object[]) query3.min(new Comparator<Object>() {

			public int compare(Object o1, Object o2) {

				if (o1 instanceof Object[] && o2 instanceof Object[]) {

					Object[] objects1 = (Object[]) o1;
					Object[] objects2 = (Object[]) o2;

					// comparison by length
					if (objects1.length < objects2.length) {
						return -1;
					} else if (objects1.length > objects2.length) {
						return 1;
					}

					for (int i = 0; i < objects1.length; i++) {

						Object object1 = objects1[i];
						Object object2 = objects2[i];

						if (object1 instanceof Object[] && object2 instanceof Object[]) {
							return compare(object1, object2);
						} else if (object1.hashCode() < object2.hashCode()) {
							return -1;
						} else if (object1.hashCode() > object2.hashCode()) {
							return 1;
						}

					}

				}

				return 0;
			}
		}));

		storage.getTransaction().close();

	}

	@Test
	public final void testCount() {

		storage.getTransaction().begin();

		assertEquals(4, storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").count());
		assertEquals(5, storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").count());
		assertEquals(1, storage
				.createQuery("'" + Polygon.class.getName() + "'( triangle, Segment0, Segment1, Segment2 )").count());

		storage.getTransaction().close();

	}

}
