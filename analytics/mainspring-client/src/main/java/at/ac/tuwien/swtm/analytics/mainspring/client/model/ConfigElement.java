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

public class ConfigElement {
    
    private String name;
    private String value;
    private boolean isDefault = false;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public boolean isEqual(ConfigElement e) {
        if (e == null)
            return false;
        return ((this.name.equals(e.name)) && 
                (this.value.equals(e.value)) &&
                (this.isDefault == e.isDefault));
    }
    
}
