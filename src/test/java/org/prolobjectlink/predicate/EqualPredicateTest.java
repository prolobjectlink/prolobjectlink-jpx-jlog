/*
 * #%L
 * >prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.predicate;

import org.junit.Test;
import org.prolobjectlink.db.predicate.EqualPredicate;
import org.prolobjectlink.domain.geometry.Point;
import org.prolobjectlink.domain.geometry.Polygon;
import org.prolobjectlink.domain.geometry.Segment;

public class EqualPredicateTest extends BasePredicateTest {

	@Test
	public final void testEvaluate() {

		assertTrue(new EqualPredicate<Point>(a), a);
		assertTrue(new EqualPredicate<Segment>(ab), ab);
		assertTrue(new EqualPredicate<Polygon>(triangle), triangle);

		assertFalse(new EqualPredicate<Point>(a), b);
		assertFalse(new EqualPredicate<Segment>(ab), bc);
		assertFalse(new EqualPredicate<Polygon>(triangle), tetragon);

		assertTrue(new EqualPredicate<Point>(a), new Point("a", 3, 14));
		assertTrue(new EqualPredicate<Segment>(ab), new Segment("ab", a, b));
		assertTrue(new EqualPredicate<Polygon>(triangle), new Polygon(new String("triangle"), ab, bc, ca));

	}

}
