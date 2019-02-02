/*
 * #%L
 * prolobjectlink-db-jlog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.prolobjectlink.prolog.jlog;

import static org.prolobjectlink.db.XmlParser.XML;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.Protocol;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.jpa.JpaAbstractProvider;
import org.prolobjectlink.db.jpa.JpaEntityManagerFactory;
import org.prolobjectlink.db.jpa.JpaProperties;
import org.prolobjectlink.db.jpa.spi.JPAPersistenceXmlParser;
import org.prolobjectlink.db.memory.MemoryHierarchical;
import org.prolobjectlink.db.persistent.EmbeddedHierarchical;
import org.prolobjectlink.db.persistent.RemoteHierarchical;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public class JLogPersistenceProvider extends JpaAbstractProvider implements PersistenceProvider {

	public JLogPersistenceProvider() {
		JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();
		URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);
		Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(persistenceXml);
		for (Entry<String, PersistenceUnitInfo> info : m.entrySet()) {
			PersistenceUnitInfo unit = info.getValue();
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
