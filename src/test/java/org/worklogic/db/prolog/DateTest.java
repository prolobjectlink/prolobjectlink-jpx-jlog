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
package org.worklogic.db.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.worklogic.BaseTest;
import org.worklogic.db.prolog.PrologDate;

public class DateTest extends BaseTest {

	private Date date = new Date();
	private PrologDate dateTime = new PrologDate();
	private long time = System.currentTimeMillis();

	@Test
	public final void testHashCode() {
		assertEquals(dateTime.hashCode(), dateTime.hashCode());
	}

	@Test
	public final void testGetTime() {
		assertEquals(dateTime.getTime(), time);
	}

	@Test
	public final void testGetJavaUtilDate() {
		assertEquals(dateTime.getJavaUtilDate(), date);
	}

	@Test
	public final void testBefore() {
		assertTrue(new PrologDate(0).before(dateTime));
		assertFalse(dateTime.before(new PrologDate(0)));
	}

	@Test
	public final void testAfter() {
		assertFalse(new PrologDate(0).after(dateTime));
		assertTrue(dateTime.after(new PrologDate(0)));
	}

	@Test
	public final void testCompareTo() {
		assertEquals(-1, new PrologDate(0).compareTo(dateTime));
		assertEquals(0, dateTime.compareTo(dateTime));
		assertEquals(1, dateTime.compareTo(new PrologDate(0)));
	}

	@Test
	public final void testToString() {
		assertEquals(dateTime.toString(), date.toString());
	}

	@Test
	public final void testEqualsObject() {
		assertTrue(dateTime.equals(dateTime));
	}

}
