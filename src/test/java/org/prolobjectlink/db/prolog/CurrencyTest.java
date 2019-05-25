/*-
 * #%L
 * >prolobjectlink-jpx-jlog
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
package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

public class CurrencyTest {

	private Locale locale = Locale.getDefault();
	private Currency currency = Currency.getInstance(locale);
	private PrologCurrency prologCurrency = new PrologCurrency(currency.getCurrencyCode());

	@Test
	public final void testHashCode() {
		assertEquals(new PrologCurrency(currency.getCurrencyCode()).hashCode(), prologCurrency.hashCode());
	}

	@Test
	public final void testGetCurrencyCode() {
		assertEquals(currency.getCurrencyCode(), prologCurrency.getCurrencyCode());
	}

	@Test
	public final void testGetJavaUtilCurrency() {
		assertEquals(currency, prologCurrency.getJavaUtilCurrency());
	}

	@Test
	public final void testToString() {
		assertEquals(currency.toString(), prologCurrency.toString());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(new PrologCurrency(currency.getCurrencyCode()), prologCurrency);
	}

}
