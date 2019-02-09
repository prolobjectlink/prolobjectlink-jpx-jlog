/*
 * #%L
 * prolobjectlink-db-jlog
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
package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.db.prolog.PrologDate;

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
