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
package at.ac.tuwien.swtm.analytics.mainspring.client.model;


import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;

public class Script {
    
    protected String name;
    protected String code;
    protected String version;
    protected ModelHelper.ScriptType type;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    } 
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public ModelHelper.ScriptType getType() {
        return type;
    }
    
    public void setType(ModelHelper.ScriptType type) {
        this.type = type;
    }
    
    public boolean isEqual(Script d) {
        if (d == null) {
            return false;
        }
        if (!(this.name.equals(d.name))) {
            return false;
        }
        if (!(this.code.equals(d.code))) {
            return false;
        }
        if (!(this.version.equals(d.version))) {
            return false;
        }
        return true;
    }
}
