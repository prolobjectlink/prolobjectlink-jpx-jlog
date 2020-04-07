/*
 * #%L
 * prolobjectlink-jpx-jlog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package io.github.prolobjectlink.db.prolog.jlog;

import static io.github.prolobjectlink.db.XmlParser.XML;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.spi.PersistenceProvider;

import io.github.prolobjectlink.db.DatabaseEngine;
import io.github.prolobjectlink.db.DatabaseSchema;
import io.github.prolobjectlink.db.DatabaseUnitInfo;
import io.github.prolobjectlink.db.DatabaseUser;
import io.github.prolobjectlink.db.Protocol;
import io.github.prolobjectlink.db.Schema;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.jpa.JpaAbstractProvider;
import io.github.prolobjectlink.db.jpa.JpaEntityManagerFactory;
import io.github.prolobjectlink.db.jpa.JpaProperties;
import io.github.prolobjectlink.db.memory.MemoryHierarchical;
import io.github.prolobjectlink.db.persistent.EmbeddedHierarchical;
import io.github.prolobjectlink.db.persistent.RemoteHierarchical;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.db.xml.PersistenceXmlParser;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

public class JLogPersistenceProvider extends JpaAbstractProvider implements PersistenceProvider {

	public JLogPersistenceProvider() {
		PersistenceXmlParser p = new PersistenceXmlParser();
		URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);
		Map<String, DatabaseUnitInfo> m = p.parsePersistenceXml(persistenceXml);
		for (Entry<String, DatabaseUnitInfo> info : m.entrySet()) {
			DatabaseUnitInfo unit = info.getValue();
			String name = unit.getPersistenceUnitName();
			Properties properties = unit.getProperties();
			Settings settings = new Settings(properties.getProperty(JpaProperties.DRIVER));
			URL url = null;
			try {
				System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
				url = new URL(properties.getProperty(JpaProperties.URL).replace(URL_PREFIX, ""));
				if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
					throw new MalformedURLException("The URL path don't have database named " + name);
				}
			} catch (MalformedURLException e) {
				LoggerUtils.error(MemoryHierarchical.class, LoggerConstants.URL, e);
			}

			assert url != null;

			// catch the rest of properties
			String password = properties.getProperty(JpaProperties.PASSWORD);
			String user = properties.getProperty(JpaProperties.USER);
			DatabaseUser owner = new DatabaseUser(user, password);
			Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
			for (String managedClass : unit.getManagedClassNames()) {
				schema.addClass(JavaReflect.classForName(managedClass), "");
			}

			DatabaseEngine e = null;
			String protocol = url.getProtocol();
			if (protocol.equalsIgnoreCase(Protocol.MEMPDB.toString())) {
				e = MemoryHierarchical.newInstance(unit, properties);
			} else if (protocol.equalsIgnoreCase(Protocol.REMPDB.toString())) {
				e = RemoteHierarchical.newInstance(unit, properties);
			} else if (protocol.equalsIgnoreCase(Protocol.FILE.toString())) {
				e = EmbeddedHierarchical.newInstance(unit, properties);
			}

			entityManagerFactories.put(name, new JpaEntityManagerFactory(e, properties));

		}
	}

}
