/*-
 * #%L
 * prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package io.github.prolobjectlink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import io.github.prolobjectlink.db.StorageMode;
import io.github.prolobjectlink.db.etc.Settings;

public class SettingsTest extends BaseTest {

	@Test
	public void testHashCode() {
		assertEquals(new Settings(driver).hashCode(), settings.hashCode());
	}

	@Test
	public void testGetContainerFactory() {
		assertNotNull(settings.getContainerFactory());
	}

	@Test
	public void testGetProvider() {
		assertNotNull(settings.getProvider());
	}

	@Test
	public void testLoad() {
		assertEquals(settings.getProvider().getClass().getName(),
				settings.load().get(Settings.class.getPackage().getName().concat(".prologProvider")));
		assertEquals(settings.getContainerFactory().getClass().getName(),
				settings.load().get(Settings.class.getPackage().getName().concat(".containerFactory")));
	}

	@Test
	public void testSave() {
		settings.save();
		File file = new File("etc" + File.separator + "prolobjectlink.xml");
		assertTrue(file.exists());
		assertTrue(file.length() > 0);
	}

	@Test
	public void testEqualsObject() {
		assertTrue(settings.equals(new Settings(driver)));
		assertFalse(settings.equals(new Object()));
		assertFalse(settings.equals(null));
	}

	@Test
	public void testCreateHierarchicalCache() {
		assertNotNull(settings.createHierarchicalCache());
	}

	@Test
	public void testCreateStorage() {
		assertNotNull(settings.createStorage(LOCATION));
	}

	@Test
	public void testCreateStoragePool() {
		assertNotNull(settings.createStoragePool(POOL_ROOT, POOL_NAME));
	}

	@Test
	public void testCreateStorageManager() {
		assertNotNull(settings.createStorageManager(ROOT, StorageMode.STORAGE_POOL));
	}

	@Test
	public void testCreateRelationalDatabase() {
		assertNotNull(settings.createRelationalDatabase(StorageMode.STORAGE_POOL, "test", user));
	}

	@Test
	public void testCreateHierarchicalDatabase() {
		assertNotNull(settings.createHierarchicalDatabase(StorageMode.STORAGE_POOL, "test", user));
	}

	@Test
	public void testPutObjectObject() {

		assertNull(settings.put(zero, "zero"));
		assertNull(settings.put(one, "one"));
		assertNull(settings.put(two, "two"));
		assertNull(settings.put(three, "three"));

		assertEquals("zero", settings.put(zero, "zero"));
		assertEquals("one", settings.put(one, "one"));
		assertEquals("two", settings.put(two, "two"));
		assertEquals("three", settings.put(three, "three"));

	}

	@Test
	public void testEntrySet() {
		assertTrue(settings.entrySet().size() > 0);
	}

}
