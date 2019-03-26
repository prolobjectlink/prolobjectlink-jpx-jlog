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
package org.prolobjectlink.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.domain.classes.OuterInnerClass;

public class InnerClassPersistenceTest extends BaseTest {

	@Test
	public final void testOuterClass() {

		storage.getTransaction().begin();
		storage.insert(new OuterInnerClass(60));
		assertTrue(storage.contains(new OuterInnerClass(60)));
		assertEquals(new OuterInnerClass(60), storage.find(new OuterInnerClass(60)));
		storage.delete(new OuterInnerClass(60));
		assertFalse(storage.contains(new OuterInnerClass(60)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testPublicInnerClass() {

		storage.getTransaction().begin();
		storage.insert(new OuterInnerClass(60).new PublicInnerClass(100));
		assertTrue(storage.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		assertEquals(new OuterInnerClass(60).new PublicInnerClass(100),
				storage.find(new OuterInnerClass(60).new PublicInnerClass(100)));
		storage.delete(new OuterInnerClass(60).new PublicInnerClass(100));
		assertFalse(storage.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testPublicInnerClass2() {

		storageManager.getTransaction().begin();
		storageManager.insert(new OuterInnerClass(60).new PublicInnerClass(100));
		assertTrue(storageManager.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		assertEquals(new OuterInnerClass(60).new PublicInnerClass(100),
				storageManager.createQuery((new OuterInnerClass(60).new PublicInnerClass(100))).getSolution());
		storageManager.delete(new OuterInnerClass(60).new PublicInnerClass(100));
		assertFalse(storageManager.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testPrivateInnerClass() {

		storage.getTransaction().begin();
		storage.insert(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertTrue(storage.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		assertEquals(new OuterInnerClass(60).newPrivateInnerClass(100),
				storage.find(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storage.delete(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertFalse(storage.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	@Ignore
	public final void testPrivateInnerClass2() {

		storageManager.getTransaction().begin();
		storageManager.insert(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertTrue(storageManager.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		assertEquals(new OuterInnerClass(60).newPrivateInnerClass(100),
				storageManager.createQuery(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storageManager.delete(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertFalse(storageManager.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

}
