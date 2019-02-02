package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import org.prolobjectlink.db.prolog.PrologCurrency;

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
