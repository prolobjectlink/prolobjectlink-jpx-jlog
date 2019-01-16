/*
 * #%L
 * prolobjectlink-jpd
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.worklogic.predicate;

import org.junit.Test;
import org.worklogic.db.predicate.EqualPredicate;
import org.worklogic.domain.geometry.Point;
import org.worklogic.domain.geometry.Polygon;
import org.worklogic.domain.geometry.Segment;

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
