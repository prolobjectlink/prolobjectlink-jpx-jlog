/*
 * #%L
 * prolobjectlink-db-jlog
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.worklogic.prolog.jlog;

import static org.worklogic.db.XmlParser.XML;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.worklogic.db.DatabaseEngine;
import org.worklogic.db.DatabaseSchema;
import org.worklogic.db.DatabaseUser;
import org.worklogic.db.Protocol;
import org.worklogic.db.Schema;
import org.worklogic.db.etc.Settings;
import org.worklogic.db.jpa.JpaAbstractProvider;
import org.worklogic.db.jpa.JpaEntityManagerFactory;
import org.worklogic.db.jpa.JpaProperties;
import org.worklogic.db.jpa.spi.JPAPersistenceXmlParser;
import org.worklogic.db.memory.MemoryHierarchical;
import org.worklogic.db.persistent.EmbeddedHierarchical;
import org.worklogic.db.persistent.RemoteHierarchical;
import org.worklogic.db.util.JavaReflect;
import org.worklogic.logging.LoggerConstants;
import org.worklogic.logging.LoggerUtils;

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
