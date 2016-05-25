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
import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="script")
public class ScriptAdapter {
    protected Script script = new Script();
    
    public ScriptAdapter() {
    }
    
    public ScriptAdapter(final Script script) {
        this.script = script;
    }
    
    public Script resolveEntity() {
        return script;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return script.getName();
    }
    
    public void setName(String name) {
        script.setName(name);
    }
    
    @XmlElement(name = "code")
    public String getCode() {
        return script.getCode();
    }
    
    public void setCode(String code) {
        script.setCode(code);
    }
    
    @XmlElement(name = "version")
    public String getVersion() {
        return script.getVersion();
    }
    
    public void setVersion(String version) {
        script.setVersion(version);
    }
    
    @XmlElement(name = "type")
    public String getType() {
        return script.getType().toString();
    }
    
    public void setType(String type) {
        script.setType(ModelHelper.ScriptType.valueOf(type));
    }
    
}
