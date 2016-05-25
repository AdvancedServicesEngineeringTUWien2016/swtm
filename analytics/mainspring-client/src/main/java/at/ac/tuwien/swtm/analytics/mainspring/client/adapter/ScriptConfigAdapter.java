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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.ScriptConfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="script")
public class ScriptConfigAdapter {
    protected ScriptConfig sc = new ScriptConfig();
    
    public ScriptConfigAdapter() {
    }
    
    public ScriptConfigAdapter(final ScriptConfig sc) {
        this.sc = sc;
    }
    
    public ScriptConfig resolveEntity() {
        return sc;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return sc.getName();
    }
    
    public void setName(String name) {
        sc.setName(name);
    }
    
    @XmlElement(name = "version")
    public String getVersion() {
        return sc.getVersion();
    }
    
    public void setVersion(String version) {
        sc.setVersion(version);
    }
    
}
