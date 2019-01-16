package org.worklogic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.logicware.prolog.PrologEngine;
import org.worklogic.db.Predicate;
import org.worklogic.db.ProcedureQuery;
import org.worklogic.domain.geometry.Point;
import org.worklogic.domain.geometry.Polygon;
import org.worklogic.domain.geometry.Segment;

public class StorageTest extends BaseTest {

	@Test
	public final void testInsertObject() {

		storage.getTransaction().begin();

		storage.delete(a);
		storage.delete(b);
		storage.delete(c);
		storage.delete(d);
		storage.delete(ab);
		storage.delete(bc);
		storage.delete(ca);
		storage.delete(cd);
		storage.delete(da);
		storage.delete(triangle);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(d));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testUpdate() {

		// create new update objects

		Point newA = new Point("a", 6, 28);
		Point newB = new Point("b", 6, 28);
		Point newC = new Point("c", 6, 28);
		Point newD = new Point("d", 6, 28);

		Segment newAB = new Segment("ab", newA, newB);
		Segment newBC = new Segment("bc", newB, newC);
		Segment newCA = new Segment("ca", newC, newA);
		Segment newCD = new Segment("cd", newC, newD);
		Segment newDA = new Segment("da", newD, newA);

		Polygon newTriangle = new Polygon("triangle", newAB, newBC, newCA);

		storage.getTransaction().begin();

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(d));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));

		assertFalse(storage.contains(newA));
		assertFalse(storage.contains(newB));
		assertFalse(storage.contains(newC));
		assertFalse(storage.contains(newD));
		assertFalse(storage.contains(newAB));
		assertFalse(storage.contains(newBC));
		assertFalse(storage.contains(newCA));
		assertFalse(storage.contains(newCD));
		assertFalse(storage.contains(newDA));
		assertFalse(storage.contains(newTriangle));

		// updating

		storage.update(a, newA);
		storage.update(b, newB);
		storage.update(c, newC);
		storage.update(d, newD);
		storage.update(ab, newAB);
		storage.update(bc, newBC);
		storage.update(ca, newCA);
		storage.update(cd, newCD);
		storage.update(da, newDA);
		storage.update(triangle, newTriangle);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(d));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		assertTrue(storage.contains(newA));
		assertTrue(storage.contains(newB));
		assertTrue(storage.contains(newC));
		assertTrue(storage.contains(newD));
		assertTrue(storage.contains(newAB));
		assertTrue(storage.contains(newBC));
		assertTrue(storage.contains(newCA));
		assertTrue(storage.contains(newCD));
		assertTrue(storage.contains(newDA));
		assertTrue(storage.contains(newTriangle));

		// reverting update changes

		storage.update(newA, a);
		storage.update(newB, b);
		storage.update(newC, c);
		storage.update(newD, d);
		storage.update(newAB, ab);
		storage.update(newBC, bc);
		storage.update(newCA, ca);
		storage.update(newCD, cd);
		storage.update(newDA, da);
		storage.update(newTriangle, triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(d));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertFalse(storage.contains(newA));
		assertFalse(storage.contains(newB));
		assertFalse(storage.contains(newC));
		assertFalse(storage.contains(newD));
		assertFalse(storage.contains(newAB));
		assertFalse(storage.contains(newBC));
		assertFalse(storage.contains(newCA));
		assertFalse(storage.contains(newCD));
		assertFalse(storage.contains(newDA));
		assertFalse(storage.contains(newTriangle));

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testDeleteObject() {

		storage.getTransaction().begin();

		storage.delete(a);
		storage.delete(b);
		storage.delete(c);
		storage.delete(d);
		storage.delete(ab);
		storage.delete(bc);
		storage.delete(ca);
		storage.delete(cd);
		storage.delete(da);
		storage.delete(triangle);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(d));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testDelete() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(Point.class));
		assertFalse(storage.contains(Segment.class));
		assertFalse(storage.contains(Polygon.class));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(ca);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(Point.class));
		assertTrue(storage.contains(Segment.class));
		assertTrue(storage.contains(Polygon.class));

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testContainsString() {

		storage.getTransaction().begin();
		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertTrue(storage.contains("'" + Point.class.getName() + "'(Idp, X, Y)"));
		assertTrue(storage.contains("'" + Point.class.getName() + "'( a, 3,14 )"));
		assertTrue(storage.contains("'" + Point.class.getName() + "'( b, 3,14 )"));
		assertTrue(storage.contains("'" + Point.class.getName() + "'( c, 3,14 )"));

		assertTrue(storage.contains("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));
		assertTrue(storage.contains("'" + Segment.class.getName() + "'( ab, '" + Point.class.getName()
				+ "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) )"));
		assertTrue(storage.contains("'" + Segment.class.getName() + "'( bc, '" + Point.class.getName()
				+ "'( b, 3,14 ), '" + Point.class.getName() + "'( c, 3,14 ) )"));
		assertTrue(storage.contains("'" + Segment.class.getName() + "'( ca, '" + Point.class.getName()
				+ "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) )"));

		assertTrue(storage.contains("'" + Polygon.class.getName() + "'( triangle, Segment0, Segment1, Segment2 )"));
		assertTrue(storage.contains("'" + Polygon.class.getName() + "'( triangle, '" + Segment.class.getName()
				+ "'( ab, '" + Point.class.getName() + "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) ), '"
				+ Segment.class.getName() + "'( bc, '" + Point.class.getName() + "'( b, 3,14 ), '"
				+ Point.class.getName() + "'( c, 3,14 ) ), '" + Segment.class.getName() + "'( ca, '"
				+ Point.class.getName() + "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) ) )"));

		assertTrue(storage.contains(
				"'" + Segment.class.getName() + "'(Ids, Point0, Point1), '" + Point.class.getName() + "'(Idp, X, Y)"));
		storage.getTransaction().commit();

	}

	@Test
	public final void testContainsObject() {

		storage.getTransaction().begin();
		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(triangle));
		storage.getTransaction().commit();

	}

	@Test
	public final void testContainsClassOfQ() {

		storage.getTransaction().begin();
		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertTrue(storage.contains(Point.class));
		assertTrue(storage.contains(Segment.class));
		assertTrue(storage.contains(Polygon.class));
		storage.getTransaction().commit();

	}

	@Test
	public final void testFindString() {

		storage.getTransaction().begin();
		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertArrayEquals(new Object[] { a }, (Object[]) storage.find("'" + Point.class.getName() + "'(Idp, X, Y)"));

		// point query restricted to specifics x and y points
		assertArrayEquals(new Object[] { a },
				(Object[]) storage.find("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14"));

		assertArrayEquals(new Object[] { ab },
				(Object[]) storage.find("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));

		// segment query restricted to specifics points initial and finals
		Object solution = storage.find("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");
		storage.getTransaction().commit();

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

		storage.getTransaction().begin();
		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertEquals(a, storage.find(a));
		assertEquals(b, storage.find(b));
		assertEquals(c, storage.find(c));
		assertEquals(d, storage.find(d));
		assertEquals(ab, storage.find(ab));
		assertEquals(bc, storage.find(bc));
		assertEquals(ca, storage.find(ca));
		assertEquals(triangle, storage.find(triangle));

		assertEquals(a, storage.find(new Point(3, 14)));
		assertEquals(ab, storage.find(new Segment()));
		assertEquals(triangle, storage.find(new Polygon()));
		storage.getTransaction().commit();

	}

	@Test
	public final void testFindClassOfS() {

		storage.getTransaction().begin();
		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertEquals(a, storage.find(Point.class));
		assertEquals(ab, storage.find(Segment.class));
		assertEquals(triangle, storage.find(Polygon.class));
		storage.getTransaction().commit();

	}

	@Test
	public final void testFindPredicateOfS() {

		storage.getTransaction().begin();

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(triangle);

		Point point = storage.find(new Predicate<Point>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 133421597975439414L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		});

		assertEquals(a, point);

		Segment segment = storage.find(new Predicate<Segment>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1842009309630793023L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		});

		assertEquals(bc, segment);

		Polygon polygon = storage.find(new Predicate<Polygon>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7860166296600275628L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		});

		assertEquals(triangle, polygon);

		storage.getTransaction().commit();

	}

	@Test
	public final void testFindAllString() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		List<Object> solutions = new ArrayList<Object>();

		solutions = storage.findAll("'" + Point.class.getName() + "'(Idp, X, Y)");
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		// point query restricted to specifics x and y points
		solutions = storage.findAll("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14");
		assertEquals(Arrays.asList(a, b, c, d), createList(solutions));

		solutions = storage.findAll("'" + Segment.class.getName() + "'(Ids, Point0, Point1)");
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(solutions));

		// segment query restricted to specifics points initial and finals
		solutions = storage.findAll("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
				+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)");
		assertEquals(Arrays.asList(ab), createList(solutions));

		// predicate projection
		solutions = storage.findAll(
				"'" + Point.class.getName() + "'(Idp, X, Y), '" + Segment.class.getName() + "'(Ids, Point0, Point1)");

		storage.getTransaction().commit();
		storage.getTransaction().close();

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

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storage.findAll("'" + Point.class.getName() + "'", null, 3, 14));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da),
				storage.findAll("'" + Segment.class.getName() + "'", null, null, null));
		assertEquals(Arrays.asList(triangle),
				storage.findAll("'" + Polygon.class.getName() + "'", null, null, null, null));

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testFindAllS() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storage.findAll(new Point(3, 14)));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storage.findAll(new Segment()));
		assertEquals(Arrays.asList(triangle), storage.findAll(new Polygon()));

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testFindAllClassOfS() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storage.findAll(Point.class));
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storage.findAll(Segment.class));
		assertEquals(Arrays.asList(triangle), storage.findAll(Polygon.class));

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testFindAllPredicateOfS() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		List<Point> points = storage.findAll(new Predicate<Point>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -9011203015766433663L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}
		});

		assertEquals(Arrays.asList(a), points);

		List<Segment> segments = storage.findAll(new Predicate<Segment>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5566947942907418523L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}
		});

		assertEquals(Arrays.asList(bc), segments);

		List<Polygon> polygons = storage.findAll(new Predicate<Polygon>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2232870748811917830L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}
		});

		assertEquals(Arrays.asList(triangle), polygons);

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testCreateQueryString() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		List<Object> objects = new ArrayList<Object>();

		objects = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y)").getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		// point query restricted to specifics x and y points
		objects = storage.createQuery("'" + Point.class.getName() + "'(Idp, X, Y), X =:= 3, Y =:= 14").getSolutions();
		assertEquals(Arrays.asList(a, b, c, d), createList(objects));

		objects = storage.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1)").getSolutions();
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), createList(objects));

		// segment query restricted to specifics points initial and finals
		objects = storage
				.createQuery("'" + Segment.class.getName() + "'(Ids, Point0, Point1), Point0 == '"
						+ Point.class.getName() + "'(a, 3,14), Point1 == '" + Point.class.getName() + "'(b, 3,14)")
				.getSolutions();
		assertEquals(Arrays.asList(ab), createList(objects));

		// predicate projection
		List<Point> points = new ArrayList<Point>();
		List<Segment> segments = new ArrayList<Segment>();
		objects = storage.createQuery(
				"'" + Segment.class.getName() + "'(Ids, Point0, Point1), '" + Point.class.getName() + "'(Idp, X, Y)")
				.getSolutions();

		storage.getTransaction().commit();
		storage.getTransaction().close();

		for (Object object : objects) {
			if (object instanceof Object[]) {
				Object[] array = (Object[]) object;
				if (array.length > 1) {
					Object object0 = array[0];
					Object object1 = array[1];
					if (object0 instanceof Segment) {
						Segment segment = (Segment) object0;
						segments.add(segment);
					}
					if (object1 instanceof Point) {
						Point point = (Point) object1;
						points.add(point);
					}
				}
			}
		}

		// all combination created by the Projection or Cartesian Product
		assertEquals(Arrays.asList(ab, ab, ab, ab, bc, bc, bc, bc, ca, ca, ca, ca, cd, cd, cd, cd, da, da, da, da),
				segments);
		assertEquals(Arrays.asList(a, b, c, d, a, b, c, d, a, b, c, d, a, b, c, d, a, b, c, d), points);

	}

	@Test
	public final void testCreateQueryS() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertEquals(a, storage.createQuery(a).getSolution());
		assertEquals(b, storage.createQuery(b).getSolution());
		assertEquals(c, storage.createQuery(c).getSolution());
		assertEquals(d, storage.createQuery(d).getSolution());
		assertEquals(ab, storage.createQuery(ab).getSolution());
		assertEquals(bc, storage.createQuery(bc).getSolution());
		assertEquals(ca, storage.createQuery(ca).getSolution());
		assertEquals(cd, storage.createQuery(cd).getSolution());
		assertEquals(da, storage.createQuery(da).getSolution());

		assertEquals(triangle, storage.createQuery(triangle).getSolution());

		assertEquals(Arrays.asList(a, b, c, d), storage.createQuery(new Point(3, 14)).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storage.createQuery(new Segment()).getSolutions());
		assertEquals(Arrays.asList(triangle), storage.createQuery(new Polygon()).getSolutions());

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testCreateQueryClassOfS() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		assertEquals(Arrays.asList(a, b, c, d), storage.createQuery(Point.class).getSolutions());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), storage.createQuery(Segment.class).getSolutions());
		assertEquals(Arrays.asList(triangle), storage.createQuery(Polygon.class).getSolutions());

		storage.getTransaction().commit();

	}

	@Test
	public final void testCreateQueryPredicateOfS() {

		storage.getTransaction().begin();

		Point point = storage.createQuery(new Predicate<Point>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7260681992007923952L;

			public boolean evaluate(Point point) {
				return point.getIdp().equals("a");
			}

		}).getSolution();

		assertEquals(a, point);

		Segment segment = storage.createQuery(new Predicate<Segment>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3581901072407255807L;

			public boolean evaluate(Segment s) {
				return s.getIds().equals("bc");
			}

		}).getSolution();

		assertEquals(bc, segment);

		Polygon polygon = storage.createQuery(new Predicate<Polygon>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2779755006479595292L;

			public boolean evaluate(Polygon s) {
				return s.getId().equals("triangle");
			}

		}).getSolution();

		assertEquals(triangle, polygon);

		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	@Ignore
	public final void testCreateProcedureQuery() {

		ProcedureQuery query = storage.createProcedureQuery("'" + Point.class.getName() + "'", "Idp", "X", "Y");
		query.setMaxSolution(2);
		query.setFirstSolution(2);
		query.execute();

		assertTrue(query.hasNext());
		Object object = query.next();
		assertTrue(object instanceof Object[]);
		Object[] objects = (Object[]) object;
		assertEquals("a", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("a", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("a", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("b", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("b", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("b", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("c", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("c", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("c", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertTrue(query.hasNext());
		object = query.next();
		assertTrue(object instanceof Object[]);
		objects = (Object[]) object;
		assertEquals("d", objects[0]);
		assertEquals(3, objects[1]);
		assertEquals(14, objects[2]);
		assertEquals("d", query.getArgumentValue(0));
		assertEquals(3, query.getArgumentValue(1));
		assertEquals(14, query.getArgumentValue(2));
		assertEquals("d", query.getArgumentValue("Idp"));
		assertEquals(3, query.getArgumentValue("X"));
		assertEquals(14, query.getArgumentValue("Y"));

		assertFalse(query.hasNext());

	}

	@Test
	public final void testCreateAndRestoreBackup() {

		storage.getTransaction().begin();

		storage.delete(Point.class);
		storage.delete(Segment.class);
		storage.delete(Polygon.class);

		assertFalse(storage.contains(a));
		assertFalse(storage.contains(b));
		assertFalse(storage.contains(c));
		assertFalse(storage.contains(ab));
		assertFalse(storage.contains(bc));
		assertFalse(storage.contains(ca));
		assertFalse(storage.contains(cd));
		assertFalse(storage.contains(da));
		assertFalse(storage.contains(triangle));

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));

		storage.getTransaction().commit();
		storage.getTransaction().close();

		// make backup
		storage.backup(BACKUP_DIRECTORY, BACKUP_ZIP_FILE_NAME_2);

		// Physic existence test
		File file = new File(BACKUP_ZIP_FILE_PATH_2);
		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		// restore the created backup
		storage.restore(".", BACKUP_ZIP_FILE_PATH_2);

		// Logical program saved
		PrologEngine engine = provider.newEngine();
		engine.consult(LOCATION);
		assertFalse(engine.isProgramEmpty());
		assertEquals(10, engine.getProgramSize());
		engine.dispose();

	}

	@Test
	public final void testFlush() {

		storage.getTransaction().begin();

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		storage.flush();
		storage.getTransaction().commit();

		// Physic existence test
		File file = new File(LOCATION);
		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		assertTrue(storage.contains(a));
		assertTrue(storage.contains(b));
		assertTrue(storage.contains(c));
		assertTrue(storage.contains(d));
		assertTrue(storage.contains(ab));
		assertTrue(storage.contains(bc));
		assertTrue(storage.contains(ca));
		assertTrue(storage.contains(cd));
		assertTrue(storage.contains(da));
		assertTrue(storage.contains(triangle));
		storage.getTransaction().close();

	}

	@Test
	public final void testClasses() {

		storage.getTransaction().begin();

		storage.insert(a);
		storage.insert(b);
		storage.insert(c);
		storage.insert(d);
		storage.insert(ab);
		storage.insert(bc);
		storage.insert(ca);
		storage.insert(cd);
		storage.insert(da);
		storage.insert(triangle);

		storage.getTransaction().commit();

		assertEquals(3, storage.classes().size());

		storage.getTransaction().close();

	}

}
