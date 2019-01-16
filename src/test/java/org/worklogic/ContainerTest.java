package org.worklogic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.worklogic.domain.geometry.Point;
import org.worklogic.domain.geometry.Polygon;
import org.worklogic.domain.geometry.Segment;

public class ContainerTest extends BaseTest {

	@Test
	public final void testContainsString() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		assertTrue(cache.contains("'" + Point.class.getName() + "'(Idp, X, Y)"));
		assertTrue(cache.contains("'" + Point.class.getName() + "'( a, 3,14 )"));
		assertTrue(cache.contains("'" + Point.class.getName() + "'( b, 3,14 )"));
		assertTrue(cache.contains("'" + Point.class.getName() + "'( c, 3,14 )"));

		assertTrue(cache.contains("'" + Segment.class.getName() + "'(Ids, Point0, Point1)"));
		assertTrue(cache.contains("'" + Segment.class.getName() + "'( ab, '" + Point.class.getName() + "'( a, 3,14 ), '"
				+ Point.class.getName() + "'( b, 3,14 ) )"));
		assertTrue(cache.contains("'" + Segment.class.getName() + "'( bc, '" + Point.class.getName() + "'( b, 3,14 ), '"
				+ Point.class.getName() + "'( c, 3,14 ) )"));
		assertTrue(cache.contains("'" + Segment.class.getName() + "'( ca, '" + Point.class.getName() + "'( c, 3,14 ), '"
				+ Point.class.getName() + "'( a, 3,14 ) )"));

		assertTrue(cache.contains("'" + Polygon.class.getName() + "'( triangle, Segment0, Segment1, Segment2 )"));
		assertTrue(cache.contains("'" + Polygon.class.getName() + "'( triangle, '" + Segment.class.getName()
				+ "'( ab, '" + Point.class.getName() + "'( a, 3,14 ), '" + Point.class.getName() + "'( b, 3,14 ) ), '"
				+ Segment.class.getName() + "'( bc, '" + Point.class.getName() + "'( b, 3,14 ), '"
				+ Point.class.getName() + "'( c, 3,14 ) ), '" + Segment.class.getName() + "'( ca, '"
				+ Point.class.getName() + "'( c, 3,14 ), '" + Point.class.getName() + "'( a, 3,14 ) ) )"));

		assertTrue(cache.contains(
				"'" + Segment.class.getName() + "'(Ids, Point0, Point1), '" + Point.class.getName() + "'(Idp, X, Y)"));

	}

	@Test
	public final void testContainsObject() {

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

	}

	@Test
	public final void testContainsClassOfQ() {

		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(ab);
		cache.add(bc);
		cache.add(ca);
		cache.add(triangle);

		assertTrue(cache.contains(Point.class));
		assertTrue(cache.contains(Segment.class));
		assertTrue(cache.contains(Polygon.class));

	}

}
