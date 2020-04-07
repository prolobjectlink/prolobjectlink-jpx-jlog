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
package io.github.prolobjectlink.performance;

import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.StoragePool;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.prolog.jlog.JLogContainerFactory;
import io.github.prolobjectlink.domain.geometry.Point;
import io.github.prolobjectlink.prolog.Prolog;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.jlog.JLog;

public class StoragePoolPerformanceTest {

	static final Class<? extends ContainerFactory> driver = JLogContainerFactory.class;
	static final Class<? extends PrologProvider> engine = JLog.class;
	static final PrologProvider prolog = Prolog.getProvider(engine);

	public static void main(String[] args) {

		StoragePool pool = new Settings(driver).createStoragePool("stress", "test");

		Point[] array = new Point[100000];
		for (int i = 0; i < array.length; i++) {
			// array[i] = new Point("" + i + "", i, i);
			array[i] = new Point("a", i, i);
		}

		// bulk addition
		long startTimeMillis = System.currentTimeMillis();
		pool.insert(array);
		pool.flush();
		// pool.clear();
		// pool.close();
		long endTimeMillis = System.currentTimeMillis();
		float durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Bulk Add Duration: " + durationSeconds + " seconds");
		System.out.println();

		// contains
		startTimeMillis = System.currentTimeMillis();
		// pool.open();
		System.out.println(pool.contains("'" + Point.class.getName() + "'(a, 999.0, 999.0)"));
		// pool.clear();
		// pool.close();
		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Contains Duration: " + durationSeconds + " seconds");
		System.out.println();

		// find all
		startTimeMillis = System.currentTimeMillis();
		// pool.open();
		System.out.println(pool.findAll(Point.class).size());
		// pool.clear();
		// pool.close();
		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Find All Duration: " + durationSeconds + " seconds");
		System.out.println();

	}

}
