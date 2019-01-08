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
package org.logicware.predicate;

import org.junit.Test;
import org.logicware.db.predicate.FalsePredicate;
import org.logicware.db.predicate.NotNullPredicate;
import org.logicware.db.predicate.NotPredicate;
import org.logicware.db.predicate.NullPredicate;
import org.logicware.db.predicate.TruePredicate;
import org.logicware.domain.geometry.Point;

public class NotPredicateTest extends BasePredicateTest {

	@Test
	public final void testEvaluate() {

		assertTrue(new NotPredicate<Object>(new FalsePredicate<Object>()), null);
		assertTrue(new NotPredicate<Point>(new NullPredicate<Point>()), a);

		assertFalse(new NotPredicate<Object>(new TruePredicate<Object>()), null);
		assertFalse(new NotPredicate<Point>(new NotNullPredicate<Point>()), a);

	}
}
