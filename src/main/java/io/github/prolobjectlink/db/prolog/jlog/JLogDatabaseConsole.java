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

import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.prolobjectlink.db.DatabaseServer;
import io.github.prolobjectlink.web.application.GrizzlyModelGenerator;
import io.github.prolobjectlink.web.application.ModelGenerator;
import io.github.prolobjectlink.web.console.AbstractWebConsole;
import io.github.prolobjectlink.web.console.WebServerConsole;
import io.github.prolobjectlink.web.platform.GrizzlyServerControl;
import io.github.prolobjectlink.web.platform.GrizzlyWebServer;
import io.github.prolobjectlink.web.platform.WebPlatformUtil;
import io.github.prolobjectlink.web.platform.WebServerControl;
import io.github.prolobjectlink.web.platform.linux.grizzly.LinuxGrizzlyWebServer;
import io.github.prolobjectlink.web.platform.macosx.grizzly.MacosxGrizzlyWebServer;
import io.github.prolobjectlink.web.platform.win32.grizzly.Win32GrizzlyWebServer;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class JLogDatabaseConsole extends AbstractWebConsole implements WebServerConsole {

	public JLogDatabaseConsole() {
		super(new JLogDatabaseProvider());
	}

	public static void main(String[] args) {
		new JLogDatabaseConsole().run(args);
	}

	public WebServerControl getWebServerControl(int port) {
		DatabaseServer database = null;
		GrizzlyWebServer server = null;
		if (WebPlatformUtil.runOnWindows()) {
//			database = new Win32DatabaseServer();
			server = new Win32GrizzlyWebServer(port);
		} else if (WebPlatformUtil.runOnOsX()) {
//			database = new MacosxDatabaseServer();
			server = new MacosxGrizzlyWebServer(port);
		} else if (WebPlatformUtil.runOnLinux()) {
//			database = new LinuxDatabaseServer();
			server = new LinuxGrizzlyWebServer(port);
		} else {
			Logger.getLogger(GrizzlyServerControl.class.getName()).log(Level.SEVERE, null, "Not supported platfor");
			System.exit(1);
		}
		return new GrizzlyServerControl(server, database);
	}

	public ModelGenerator getModelGeneratorInstance() {
		return new GrizzlyModelGenerator();
	}

}
