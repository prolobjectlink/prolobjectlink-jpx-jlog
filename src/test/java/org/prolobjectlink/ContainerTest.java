/*-
 * #%L
 * prolobjectlink-db-jlog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.prolobjectlink;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.prolobjectlink.domain.geometry.Point;
import org.prolobjectlink.domain.geometry.Polygon;
import org.prolobjectlink.domain.geometry.Segment;

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
