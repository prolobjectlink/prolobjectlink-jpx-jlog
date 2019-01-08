package org.logicware.db.prolog;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.logicware.db.prolog.PrologCalendar;

public class CalendarTest {

	private Calendar calendar = Calendar.getInstance();
	private PrologCalendar prologCalendar = new PrologCalendar();

	@Test
	public final void testGetJavaUtilCalendar() {
		assertEquals(calendar, prologCalendar.getJavaUtilCalendar());
	}

	@Test
	public final void testGetTimeInMillis() {
		assertEquals(calendar.getTimeInMillis(), prologCalendar.getTimeInMillis());
	}

	@Test
	public final void testIsLenient() {
		assertEquals(calendar.isLenient(), prologCalendar.isLenient());
	}

	@Test
	public final void testGetFirstDayOfWeek() {
		assertEquals(calendar.getFirstDayOfWeek(), prologCalendar.getFirstDayOfWeek());
	}

	@Test
	public final void testGetMinimalDaysInFirstWeek() {
		assertEquals(calendar.getMinimalDaysInFirstWeek(), prologCalendar.getMinimalDaysInFirstWeek());
	}

}
