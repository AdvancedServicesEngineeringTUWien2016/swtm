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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.ac.tuwien.swtm.analytics.mainspring.client.model.ConfigElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="config")
public class ConfigElementAdapter {
    
    private String name;
    private String value;
    private boolean isDefault;
    
    public ConfigElementAdapter() {
    }
    
    public ConfigElementAdapter(ConfigElement element) {
        name = element.getName();
        value = element.getValue();
        isDefault = element.getIsDefault();
    }
    
    public ConfigElement resolveEntity() {
        ConfigElement element = new ConfigElement();
        element.setName(name);
        element.setValue(value);
        element.setIsDefault(isDefault);
        return element;
    }
    
    @XmlElement (name = "name")
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
       
    @XmlElement (name = "value")
    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }
    
    @XmlElement (name = "isdefault")
    @JsonProperty("isdefault")
    public boolean getIsDefault(){
        return isDefault;
    }
    
}
