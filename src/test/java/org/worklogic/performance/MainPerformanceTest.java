package org.worklogic.performance;

import org.logicware.prolog.Prolog;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.jlog.JLog;
import org.worklogic.db.ContainerFactory;
import org.worklogic.db.Storage;
import org.worklogic.db.etc.Settings;
import org.worklogic.domain.geometry.Point;
import org.worklogic.prolog.jlog.JLogContainerFactory;

public class MainPerformanceTest {

	// private static ObjectCache cache;
	private static Storage store;

	private static final int instanceNumber = 5000;

	private static final String LOCATION = "performance";
	// private static final String ROOT = "data" + File.separator + "test";

	protected static final Class<? extends ContainerFactory> driver = JLogContainerFactory.class;

	// protected static final Class<?> engine = JBPEPrologEngine.class;
	// protected static final Class<?> engine = JLogProvider.class;
	protected static final Class<? extends PrologProvider> engine = JLog.class;
	// protected static final Class<?> engine = TuPrologProvider.class;
	// protected static final Class<?> engine = SwiPrologProvider.class;
	// protected static final Class<?> engine = ZPrologProvider.class;

	protected static final PrologProvider prolog = Prolog.newProvider(engine);

	public MainPerformanceTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// cache = Prolobjectlink.create(ENGINE).createCache();
		store = new Settings(driver).createStorage(LOCATION);

		Point[] array = new Point[instanceNumber];
		for (int i = 0; i < array.length; i++) {
			// array[i] = new Point("" + i + "", i, i);
			array[i] = new Point("a", i, i);
		}

		// bulk addition
		long startTimeMillis = System.currentTimeMillis();

		// cache.insert(array);
		store.insert(array);
		store.flush();

		long endTimeMillis = System.currentTimeMillis();
		float durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Bulk Add Duration: " + durationSeconds + " seconds");
		System.out.println();

		// contains
		startTimeMillis = System.currentTimeMillis();

		int last = instanceNumber - 1;
		// cache.contains(new Point("" + last + "", last, last));
		// store.contains(new Point("" + last + "", last, last));
		// System.out.println(cache.contains(new Point("" + last + "", last,
		// last)));
		// System.out.println(store.contains(new Point("a", last, last)));

		System.out.println(store.contains("'" + Point.class.getName() + "'(a, 999.0, 999.0)"));

		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Contains Duration: " + durationSeconds + " seconds");
		System.out.println();

		// find all
		startTimeMillis = System.currentTimeMillis();

		// cache.findAll(Point.class);
		// store.findAll(Point.class);
		// System.out.println(cache.findAll(Point.class).size());
		System.out.println(store.findAll(Point.class).size());

		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Find All Duration: " + durationSeconds + " seconds");
		System.out.println();

	}

}
