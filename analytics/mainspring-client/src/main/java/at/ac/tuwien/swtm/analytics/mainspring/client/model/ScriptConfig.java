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

public class ScriptConfig {
    
    protected String name;
    protected String version;

    public ScriptConfig() {
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public boolean isEqual(ScriptConfig m) {
        if (m == null) {
            return false;
        }
        return (this.name.equals(m.name)) && (this.version.equals(m.version));
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
