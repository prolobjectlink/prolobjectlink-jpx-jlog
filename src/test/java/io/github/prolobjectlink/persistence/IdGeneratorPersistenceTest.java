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
package io.github.prolobjectlink.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import io.github.prolobjectlink.BaseTest;
import io.github.prolobjectlink.db.generator.IncrementGenerator;
import io.github.prolobjectlink.db.generator.TimestampGenerator;
import io.github.prolobjectlink.db.generator.UUIDGenerator;
import io.github.prolobjectlink.domain.geometry.Point;

public class IdGeneratorPersistenceTest extends BaseTest {

	private IncrementGenerator incGenerator = new IncrementGenerator(Point.class);
	private TimestampGenerator timeGenerator = new TimestampGenerator(Point.class);
	private UUIDGenerator uuidGenerator = new UUIDGenerator(Point.class);

	@Test
	public final void testIncrement() {

		storage.getTransaction().begin();
		storage.insert(incGenerator);
		assertTrue(storage.contains(incGenerator));
		assertEquals(incGenerator, storage.find(incGenerator));
		storage.delete(incGenerator);
		assertFalse(storage.contains(new IncrementGenerator(Point.class)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	@Ignore
	public final void testTimestamp() {

		storage.getTransaction().begin();
		storage.insert(timeGenerator);
		assertTrue(storage.contains(timeGenerator));
		assertEquals(timeGenerator, storage.find(timeGenerator));
		storage.delete(timeGenerator);
		assertFalse(storage.contains(new TimestampGenerator(Point.class)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testUUID() {

		storage.getTransaction().begin();
		storage.insert(uuidGenerator);
		assertTrue(storage.contains(uuidGenerator));
		assertEquals(uuidGenerator, storage.find(uuidGenerator));
		storage.delete(uuidGenerator);
		assertFalse(storage.contains(uuidGenerator));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

}
