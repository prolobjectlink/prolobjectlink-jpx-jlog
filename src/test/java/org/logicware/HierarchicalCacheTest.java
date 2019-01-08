package org.logicware;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.logicware.db.Predicate;
import org.logicware.domain.geometry.Point;
import org.logicware.domain.geometry.Polygon;
import org.logicware.domain.geometry.Segment;

public class HierarchicalCacheTest extends BaseTest {

	@Test
	public final void testAddObjectArray() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(cd);
		cache.add(da);
		cache.add(triangle);
		// objectContainer.add(new Point());
		// objectContainer.add(new Point("z"));
		// objectContainer.add(new Point(3.5F, 10.14F));
		// objectContainer.add(new Segment());
		// objectContainer.add(new Segment("zz"));
		// objectContainer.add(new Segment(a, b));
		// objectContainer.add(new Polygon());

		assertTrue(cache.contains(a));
		assertTrue(cache.contains(b));
		assertTrue(cache.contains(c));
		assertTrue(cache.contains(ab));
		assertTrue(cache.contains(bc));
		assertTrue(cache.contains(ca));
		assertTrue(cache.contains(triangle));
		// assertTrue(objectContainer.contains(new Point()));
		// assertTrue(objectContainer.contains(new Point("z")));
		// assertTrue(objectContainer.contains(new Point(3.5F, 10.14F)));
		// assertTrue(objectContainer.contains(new Segment()));
		// assertTrue(objectContainer.contains(new Segment("zz")));
		// assertTrue(objectContainer.contains(new Segment(a, b)));
		// assertTrue(objectContainer.contains(new Polygon()));

	}

	@Test
	public final void testModify() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		// create new update objects

		Point newA = new Point("a", 6,28);
		Point newB = new Point("b", 6,28);
		Point newC = new Point("c", 6,28);

		Segment newAB = new Segment("ab", newA, newB);
		Segment newBC = new Segment("bc", newB, newC);
		Segment newCA = new Segment("ca", newC, newA);

		Polygon newTriangle = new Polygon("triangle", newAB, newBC, newCA);

		assertTrue(cache.contains(a));
		assertTrue(cache.contains(b));
		assertTrue(cache.contains(c));
		assertTrue(cache.contains(ab));
		assertTrue(cache.contains(bc));
		assertTrue(cache.contains(ca));
		assertTrue(cache.contains(triangle));

		assertFalse(cache.contains(newA));
		assertFalse(cache.contains(newB));
		assertFalse(cache.contains(newC));
		assertFalse(cache.contains(newAB));
		assertFalse(cache.contains(newBC));
		assertFalse(cache.contains(newCA));
		assertFalse(cache.contains(newTriangle));

		// updating

		cache.modify(a, newA);
		cache.modify(b, newB);
		cache.modify(c, newC);
		cache.modify(ab, newAB);
		cache.modify(bc, newBC);
		cache.modify(ca, newCA);
		cache.modify(triangle, newTriangle);

		assertFalse(cache.contains(a));
		assertFalse(cache.contains(b));
		assertFalse(cache.contains(c));
		assertFalse(cache.contains(ab));
		assertFalse(cache.contains(bc));
		assertFalse(cache.contains(ca));
		assertFalse(cache.contains(triangle));

		assertTrue(cache.contains(newA));
		assertTrue(cache.contains(newB));
		assertTrue(cache.contains(newC));
		assertTrue(cache.contains(newAB));
		assertTrue(cache.contains(newBC));
		assertTrue(cache.contains(newCA));
		assertTrue(cache.contains(newTriangle));

		// reverting update changes

		cache.modify(newA, a);
		cache.modify(newB, b);
		cache.modify(newC, c);
		cache.modify(newAB, ab);
		cache.modify(newBC, bc);
		cache.modify(newCA, ca);
		cache.modify(newTriangle, triangle);

		assertTrue(cache.contains(a));
		assertTrue(cache.contains(b));
		assertTrue(cache.contains(c));
		assertTrue(cache.contains(ab));
		assertTrue(cache.contains(bc));
		assertTrue(cache.contains(ca));
		assertTrue(cache.contains(triangle));

		assertFalse(cache.contains(newA));
		assertFalse(cache.contains(newB));
		assertFalse(cache.contains(newC));
		assertFalse(cache.contains(newAB));
		assertFalse(cache.contains(newBC));
		assertFalse(cache.contains(newCA));
		assertFalse(cache.contains(newTriangle));

	}

	@Test
	public final void testRemoveObjectArray() {

		cache.remove(a);
		cache.remove(b);
		cache.remove(c);
		cache.remove(ab);
		cache.remove(bc);
		cache.remove(ca);
		cache.remove(triangle);

		assertFalse(cache.contains(a));
		assertFalse(cache.contains(b));
		assertFalse(cache.contains(c));
		assertFalse(cache.contains(ab));
		assertFalse(cache.contains(bc));
		assertFalse(cache.contains(ca));
		assertFalse(cache.contains(triangle));

	}

	@Test
	public final void testRemoveClassOfQ() {

		cache.remove(Point.class);
		cache.remove(Segment.class);
		cache.remove(Polygon.class);

		assertFalse(cache.contains(Point.class));
		assertFalse(cache.contains(Segment.class));
		assertFalse(cache.contains(Polygon.class));

	}

	@Test
	public final void testFindString() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		assertArrayEquals(new Object[] { a }, (Object[]) cache.find("'" + Point.class.getName() + "'(Idp, X, Y)"));

		// point query restricted to specifics x and y points
		assertArrayEquals(new Object[] { a },
				(Object[]) cache.find("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14"));

		assertArrayEquals(new Object[] { ab },
				(Object[]) cache.find("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));

		// segment query restricted to specifics points initial and finals
		Object solution = cache.find("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");

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

	}

	@Test
	public final void testFindS() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		assertEquals(a, cache.find(a));
		assertEquals(b, cache.find(b));
		assertEquals(c, cache.find(c));
		assertEquals(d, cache.find(d));
		assertEquals(ab, cache.find(ab));
		assertEquals(bc, cache.find(bc));
		assertEquals(ca, cache.find(ca));
		assertEquals(triangle, cache.find(triangle));

		assertEquals(a, cache.find(new Point(3,14)));
		assertEquals(ab, cache.find(new Segment()));
		assertEquals(triangle, cache.find(new Polygon()));

	}

	@Test
	public final void testFindClassOfS() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		assertEquals(a, cache.find(Point.class));
		assertEquals(ab, cache.find(Segment.class));
		assertEquals(triangle, cache.find(Polygon.class));

	}

	@Test
	public final void testFindPredicateOfS() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		Point point = cache.find(new Predicate<Point>() {

			private static final long serialVersionUID = -470868260302249092L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		});

		assertEquals(a, point);

		Segment segment = cache.find(new Predicate<Segment>() {

			private static final long serialVersionUID = 5706469992732116423L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		});

		assertEquals(bc, segment);

		Polygon polygon = cache.find(new Predicate<Polygon>() {

			private static final long serialVersionUID = -1461993867016929305L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		});

		assertEquals(triangle, polygon);

	}

	@Test
	public final void testFindAllString() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(cd);
		cache.add(da);
		cache.add(triangle);

		List<Object> solutions = new ArrayList<Object>();

		solutions = cache.findAll("'" + Point.class.getName() + "'(Idp, X, Y)");
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		// point query restricted to specifics x and y points
		solutions = cache.findAll("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14");
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		solutions = cache.findAll("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(solutions));

		// segment query restricted to specifics points initial and finals
		solutions = cache.findAll("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");
		assertEquals(Arrays.asList(ab), createList(solutions));

		// predicate projection
		solutions = cache.findAll(
				"'" + Point.class.getName() + "'(Idp, X, Y), '" + Segment.class.getName() + "'(Ids, Point0, Point1)");

		List<Point> points = new ArrayList<Point>();
		List<Segment> segments = new ArrayList<Segment>();
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

		// all combination created by the Projection or Cartesian Product
		assertEquals(Arrays.asList(a, a, a, a, a, b, b, b, b, b, c, c, c, c, c, d, d, d, d, d), points);
		assertEquals(Arrays.asList(ab, bc, ca, cd, da, ab, bc, ca, cd, da, ab, bc, ca, cd, da, ab, bc, ca, cd, da),
				segments);

	}

	@Test
	public final void testFindAllStringObjectArray() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(cd);
		cache.add(da);
		cache.add(triangle);

		assertEquals(Arrays.asList(a, b, c, d), cache.findAll("'" + Point.class.getName() + "'", null, 3,14));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da),
				cache.findAll("'" + Segment.class.getName() + "'", null, null, null));
		assertEquals(Arrays.asList(triangle),
				cache.findAll("'" + Polygon.class.getName() + "'", null, null, null, null));

	}

	@Test
	public final void testFindAllS() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(cd);
		cache.add(da);
		cache.add(triangle);

		assertEquals(Arrays.asList(a, b, c, d), cache.findAll(new Point(3,14)));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), cache.findAll(new Segment()));
		assertEquals(Arrays.asList(triangle), cache.findAll(new Polygon()));

	}

	@Test
	public final void testFindAllClassOfS() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(cd);
		cache.add(da);
		cache.add(triangle);

		assertEquals(Arrays.asList(a, b, c, d), cache.findAll(Point.class));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), cache.findAll(Segment.class));
		assertEquals(Arrays.asList(triangle), cache.findAll(Polygon.class));

	}

	@Test
	public final void testFindAllPredicateOfS() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(cd);
		cache.add(da);
		cache.add(triangle);

		List<Point> points = cache.findAll(new Predicate<Point>() {

			private static final long serialVersionUID = 1331072681879127348L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}
		});

		assertEquals(Arrays.asList(a), points);

		List<Segment> segments = cache.findAll(new Predicate<Segment>() {

			private static final long serialVersionUID = 8877171960492653103L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}
		});

		assertEquals(Arrays.asList(bc), segments);

		List<Polygon> polygons = cache.findAll(new Predicate<Polygon>() {

			private static final long serialVersionUID = 729481999120540714L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}
		});

		assertEquals(Arrays.asList(triangle), polygons);

	}

	@Test
	public final void testClear() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		assertTrue(cache.contains(a));
		assertTrue(cache.contains(b));
		assertTrue(cache.contains(c));
		assertTrue(cache.contains(ab));
		assertTrue(cache.contains(bc));
		assertTrue(cache.contains(ca));
		assertTrue(cache.contains(triangle));

		cache.clear();

		assertFalse(cache.contains(a));
		assertFalse(cache.contains(b));
		assertFalse(cache.contains(c));
		assertFalse(cache.contains(ab));
		assertFalse(cache.contains(bc));
		assertFalse(cache.contains(ca));
		assertFalse(cache.contains(triangle));

	}

}
