/*
 * #%L
 * prolobjectlink-jlog
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpi.jlog;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.logicware.jpi.PrologClause;

final class JLogClauseEnum implements Enumeration<PrologClause> {

    private final Iterator<PrologClause> i;

    public JLogClauseEnum(Collection<PrologClause> cls) {
	i = cls.iterator();
    }

    public boolean hasMoreElements() {
	return i.hasNext();
    }

    public PrologClause nextElement() {
	return i.next();
    }

}
