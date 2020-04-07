/*
 * #%L
 * prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package io.github.prolobjectlink.predicate;

import org.junit.Test;

import io.github.prolobjectlink.db.predicate.OrPredicate;
import io.github.prolobjectlink.domain.geometry.Point;

public class OrPredicateTest extends BasePredicateTest {

	@Test
	public final void testEvaluate() {

		assertTrue(new OrPredicate<Point>(leftPredicate, rigthPredicate), a);
		assertFalse(new OrPredicate<Point>(leftPredicate, rigthPredicate), null);

	}

}
