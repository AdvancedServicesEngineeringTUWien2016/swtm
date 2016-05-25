/*
 * Copyright (C) 2011-2015 M2MLabs
 *
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
 */
package at.ac.tuwien.swtm.analytics.mainspring.client.adapter;

import at.ac.tuwien.swtm.analytics.mainspring.client.model.Script;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "scripts")
public class ScriptListAdapter {

    protected List<Script> scripts = new LinkedList<Script>();
    private boolean success;

    public ScriptListAdapter() {
    }

    public ScriptListAdapter(final List<Script> scripts) {
        this.scripts = scripts;
    }

    public void setScripts(List<Script> scripts) {
        this.scripts = scripts;
    }

    @XmlElement(name = "ScriptList")
    public List<ScriptAdapter> getScriptList() {
        List<ScriptAdapter> adapters = new LinkedList<ScriptAdapter>();
        for (final Script d : scripts) {
            adapters.add(new ScriptAdapter(d));
        }
        return adapters;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
