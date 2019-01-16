package org.worklogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.worklogic.db.TypedQuery;
import org.worklogic.domain.geometry.Point;
import org.worklogic.domain.geometry.Polygon;
import org.worklogic.domain.geometry.Segment;

public class TypedQueryTest extends BaseTest {

	@Test
	public final void testGetSolution() {

		// Structure QBE
		storage.getTransaction().begin();

		TypedQuery<Point> query1 = storage.createQuery(a);
		assertEquals(a, query1.getSolution());
		query1.dispose();

		TypedQuery<Segment> query2 = storage.createQuery(ab);
		assertEquals(ab, query2.getSolution());
		query2.dispose();

		TypedQuery<Polygon> query3 = storage.createQuery(triangle);
		assertEquals(triangle, query3.getSolution());
		query3.dispose();

		// TypedQuery<Point> query4 = objectContainer.createQuery(new Point());
		// assertEquals(a, query4.getSolution());
		// query4.dispose();

		// TypedQuery<Point> query5 = objectContainer.createQuery(new Point(new
		// String("a")));
		// assertEquals(a, query5.getSolution());
		// query5.dispose();

		TypedQuery<Point> query6 = storage.createQuery(new Point(3, 14));
		assertEquals(a, query6.getSolution());
		query6.dispose();

		TypedQuery<Segment> query7 = storage.createQuery(new Segment());
		assertEquals(ab, query7.getSolution());
		query7.dispose();

		TypedQuery<Segment> query8 = storage.createQuery(new Segment(new String("ab")));
		assertEquals(ab, query8.getSolution());
		query8.dispose();

		TypedQuery<Segment> query9 = storage.createQuery(new Segment(a, b));
		assertEquals(ab, query9.getSolution());
		query9.dispose();

		TypedQuery<Polygon> query10 = storage.createQuery(new Polygon());
		assertEquals(triangle, query10.getSolution());
		query10.dispose();

		TypedQuery<Polygon> query11 = storage.createQuery(new Polygon(new String("triangle")));
		assertEquals(triangle, query11.getSolution());
		query11.dispose();

		TypedQuery<Polygon> query12 = storage.createQuery(new Polygon(ab, bc, ca));
		assertEquals(triangle, query12.getSolution());
		query12.dispose();

		// Class query

		TypedQuery<Point> query = storage.createQuery(Point.class);
		assertEquals(a, query.getSolution());

		TypedQuery<Segment> query13 = storage.createQuery(Segment.class);
		assertEquals(ab, query13.getSolution());

		TypedQuery<Polygon> query14 = storage.createQuery(Polygon.class);
		assertEquals(triangle, query14.getSolution());

		storage.getTransaction().close();

	}

	@Test
	public final void testGetSolutions() {

		// Structure QBE

		storage.getTransaction().begin();

		TypedQuery<Point> query1 = storage.createQuery(new Point(3, 14));
		assertEquals(a, query1.getSolution());
		query1.dispose();

		TypedQuery<Segment> query2 = storage.createQuery(new Segment());
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), query2.getSolutions());
		query2.dispose();

		// Class query

		TypedQuery<Point> query3 = storage.createQuery(Point.class);
		assertEquals(Arrays.asList(a, b, c, d), query3.getSolutions());

		TypedQuery<Segment> query4 = storage.createQuery(Segment.class);
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), query4.getSolutions());

		storage.getTransaction().close();

	}

	@Test
	public final void testSetAndGetMaxSolution() {

		storage.getTransaction().begin();
		TypedQuery<Point> query2 = storage.createQuery(Point.class).setMaxSolution(2);
		assertEquals(2, query2.getMaxSolution());
		assertEquals(Arrays.asList(a, b), query2.getSolutions());
		assertEquals(2, query2.getSolutions().size());
		storage.getTransaction().close();

	}

	@Test
	public final void testSetAndGetFirstSolution() {

		storage.getTransaction().begin();

		TypedQuery<Point> query = storage.createQuery(Point.class).setFirstSolution(0);
		assertEquals(0, query.getFirstSolution());
		assertEquals(Arrays.asList(a, b, c, d), query.getSolutions());

		query = storage.createQuery(Point.class).setFirstSolution(1);
		assertEquals(1, query.getFirstSolution());
		assertEquals(Arrays.asList(b, c, d), query.getSolutions());

		query = storage.createQuery(Point.class).setFirstSolution(2);
		assertEquals(2, query.getFirstSolution());
		assertEquals(Arrays.asList(c, d), query.getSolutions());

		query = storage.createQuery(Point.class).setFirstSolution(3);
		assertEquals(3, query.getFirstSolution());
		assertEquals(Arrays.asList(d), query.getSolutions());

		query = storage.createQuery(Point.class).setFirstSolution(4);
		assertEquals(4, query.getFirstSolution());

		storage.getTransaction().close();

	}

	@Test
	public final void testOrderAscending() {

		storage.getTransaction().begin();

		TypedQuery<Point> query3 = storage.createQuery(Point.class).orderAscending();
		assertEquals(Arrays.asList(a, b, c, d), query3.getSolutions());

		TypedQuery<Segment> query4 = storage.createQuery(Segment.class).orderAscending();
		assertEquals(Arrays.asList(ab, bc, ca, cd, da), query4.getSolutions());

		storage.getTransaction().close();

	}

	@Test
	public final void testOrderDescending() {

		storage.getTransaction().begin();

		TypedQuery<Point> query3 = storage.createQuery(Point.class).orderDescending();
		assertEquals(Arrays.asList(d, c, b, a), query3.getSolutions());

		TypedQuery<Segment> query4 = storage.createQuery(Segment.class).orderDescending();
		assertEquals(Arrays.asList(da, cd, ca, bc, ab), query4.getSolutions());

		storage.getTransaction().close();

	}

	@Test
	public final void testOrderBy() {

		storage.getTransaction().begin();

		TypedQuery<Point> query1 = storage.createQuery(Point.class).orderBy(new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (o1.getIdp().compareTo(o2.getIdp()) < 0) {
					return -1;
				} else if (o1.getIdp().compareTo(o2.getIdp()) > 0) {
					return 1;
				}
				return 0;
			}
		});

		assertEquals(Arrays.asList(a, b, c, d), query1.getSolutions());

		TypedQuery<Point> query2 = storage.createQuery(Point.class).orderBy(new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (o1.getIdp().compareTo(o2.getIdp()) < 0) {
					return 1;
				} else if (o1.getIdp().compareTo(o2.getIdp()) > 0) {
					return -1;
				}
				return 0;
			}
		});

		assertEquals(Arrays.asList(d, c, b, a), query2.getSolutions());

		TypedQuery<Segment> query3 = storage.createQuery(Segment.class).orderBy(new Comparator<Segment>() {

			public int compare(Segment o1, Segment o2) {
				if (o1.getIds().compareTo(o2.getIds()) < 0) {
					return -1;
				} else if (o1.getIds().compareTo(o2.getIds()) > 0) {
					return 1;
				}
				return 0;
			}

		});

		assertEquals(Arrays.asList(ab, bc, ca, cd, da), query3.getSolutions());

		TypedQuery<Segment> query4 = storage.createQuery(Segment.class).orderBy(new Comparator<Segment>() {

			public int compare(Segment o1, Segment o2) {
				if (o1.getIds().compareTo(o2.getIds()) < 0) {
					return 1;
				} else if (o1.getIds().compareTo(o2.getIds()) > 0) {
					return -1;
				}
				return 0;
			}

		});

		assertEquals(Arrays.asList(da, cd, ca, bc, ab), query4.getSolutions());

		storage.getTransaction().close();

	}

	@Test
	public final void testDescend() {

		storage.getTransaction().begin();

		TypedQuery<Object> query = storage.createQuery(Point.class).descend("idp");
		assertEquals(Arrays.asList(new String("a"), new String("b"), new String("c"), new String("d")),
				query.getSolutions());

		query = storage.createQuery(Segment.class).descend("point0");
		assertEquals(Arrays.asList(a, b, c, c, d), query.getSolutions());

		query = storage.createQuery(Segment.class).descend("point0").descend("idp");
		assertEquals(Arrays.asList(new String("a"), new String("b"), new String("c"), new String("c"), new String("d")),
				query.getSolutions());

		query = storage.createQuery(Polygon.class).descend("segment0");
		assertEquals(Arrays.asList(ab), query.getSolutions());

		query = storage.createQuery(Polygon.class).descend("segment0").descend("point0");
		assertEquals(Arrays.asList(a), query.getSolutions());

		query = storage.createQuery(Polygon.class).descend("segment0").descend("point0").descend("idp");
		assertEquals(Arrays.asList(new String("a")), query.getSolutions());

		storage.getTransaction().close();

	}

	@Test
	public final void testhasNextAndnext() {

		storage.getTransaction().begin();

		TypedQuery<Segment> query = storage.createQuery(Segment.class);

		assertTrue(query.hasNext());
		assertEquals(ab, query.next());
		assertTrue(query.hasNext());
		assertEquals(bc, query.next());
		assertTrue(query.hasNext());
		assertEquals(ca, query.next());
		assertTrue(query.hasNext());
		assertEquals(cd, query.next());
		assertTrue(query.hasNext());
		assertEquals(da, query.next());

		assertFalse(query.hasNext());

		storage.getTransaction().close();

	}

	@Test
	public final void testMax() {

		storage.getTransaction().begin();

		TypedQuery<Point> query1 = storage.createQuery(Point.class);
		assertEquals(d, query1.max());

		TypedQuery<Segment> query4 = storage.createQuery(Segment.class);
		assertEquals(da, query4.max());

		storage.getTransaction().close();

	}

	@Test
	public final void testMaxComparatorOfT() {

		storage.getTransaction().begin();

		TypedQuery<Point> query1 = storage.createQuery(Point.class);
		assertEquals(d, query1.max(new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (o1.getIdp().compareTo(o2.getIdp()) < 0) {
					return -1;
				} else if (o1.getIdp().compareTo(o2.getIdp()) > 0) {
					return 1;
				}
				return 0;
			}
		}));

		TypedQuery<Segment> query4 = storage.createQuery(Segment.class);
		assertEquals(da, query4.max(new Comparator<Segment>() {

			public int compare(Segment o1, Segment o2) {
				if (o1.getIds().compareTo(o2.getIds()) < 0) {
					return -1;
				} else if (o1.getIds().compareTo(o2.getIds()) > 0) {
					return 1;
				}
				return 0;
			}

		}));

		storage.getTransaction().close();

	}

	@Test
	public final void testMin() {

		storage.getTransaction().begin();

		TypedQuery<Point> query2 = storage.createQuery(Point.class);
		assertEquals(a, query2.min());

		TypedQuery<Segment> query3 = storage.createQuery(Segment.class);
		assertEquals(ab, query3.min());

		storage.getTransaction().close();

	}

	@Test
	public final void testMinComparatorOfT() {

		storage.getTransaction().begin();

		TypedQuery<Point> query2 = storage.createQuery(Point.class);
		assertEquals(a, query2.min(new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (o1.getIdp().compareTo(o2.getIdp()) < 0) {
					return -1;
				} else if (o1.getIdp().compareTo(o2.getIdp()) > 0) {
					return 1;
				}
				return 0;
			}
		}));

		TypedQuery<Segment> query3 = storage.createQuery(Segment.class);

		assertEquals(ab, query3.min(new Comparator<Segment>() {

			public int compare(Segment o1, Segment o2) {
				if (o1.getIds().compareTo(o2.getIds()) < 0) {
					return -1;
				} else if (o1.getIds().compareTo(o2.getIds()) > 0) {
					return 1;
				}
				return 0;
			}

		}));

		storage.getTransaction().close();

	}

	@Test
	public final void testCount() {

		storage.getTransaction().begin();
		assertEquals(4, storage.createQuery(Point.class).count());
		assertEquals(5, storage.createQuery(Segment.class).count());
		assertEquals(1, storage.createQuery(Polygon.class).count());
		storage.getTransaction().close();

	}

	@Test
	public final void test() {

		storage.getTransaction().begin();

		TypedQuery<Point> query4 = storage.createQuery(new Point());
		// assertEquals(a, query4.getSolution());
		query4.dispose();

		TypedQuery<Point> query5 = storage.createQuery(new Point(new String("a")));
		// assertEquals(a, query5.getSolution());
		query5.dispose();

		storage.getTransaction().close();

	}

}
