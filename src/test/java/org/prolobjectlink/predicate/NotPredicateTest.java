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
import org.prolobjectlink.db.predicate.FalsePredicate;
import org.prolobjectlink.db.predicate.NotNullPredicate;
import org.prolobjectlink.db.predicate.NotPredicate;
import org.prolobjectlink.db.predicate.NullPredicate;
import org.prolobjectlink.db.predicate.TruePredicate;
import org.prolobjectlink.domain.geometry.Point;

public class NotPredicateTest extends BasePredicateTest {

	@Test
	public final void testEvaluate() {

		assertTrue(new NotPredicate<Object>(new FalsePredicate<Object>()), null);
		assertTrue(new NotPredicate<Point>(new NullPredicate<Point>()), a);

		assertFalse(new NotPredicate<Object>(new TruePredicate<Object>()), null);
		assertFalse(new NotPredicate<Point>(new NotNullPredicate<Point>()), a);

	}
}
