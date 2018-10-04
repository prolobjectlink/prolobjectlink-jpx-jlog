/*
 * #%L
 * prolobjectlink-db-jlog
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.prolog.jlog;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.HierarchicalCache;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.Settings;
import org.logicware.pdb.prolog.PrologHierarchicalCache;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public class JLogHierarchicalCache extends PrologHierarchicalCache implements HierarchicalCache {

	public JLogHierarchicalCache(PrologProvider provider, Settings settings, ContainerFactory containerFactory) {
		super(provider, settings, new JLogContainerFactory(settings));
	}

	public JLogHierarchicalCache(PrologProvider provider, Settings settings, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory) {
		super(provider, settings, converter, new JLogContainerFactory(settings));
	}

}
