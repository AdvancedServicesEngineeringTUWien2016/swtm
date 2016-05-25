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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.DeviceConfigMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="config")
public class DeviceConfigMetadataAdapter {
    
    private String name;
    private String displayName;
    private String defaultValue;
    
    public DeviceConfigMetadataAdapter() {
    }
    
    public DeviceConfigMetadataAdapter(DeviceConfigMetadata element) {
        name = element.getName();
        displayName = element.getDisplayName();
        defaultValue = element.getDefaultValue();
    }
    
    @XmlElement (name = "name")
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
       
    @XmlElement (name = "displayname")
    @JsonProperty("displayname")
    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String value){
        this.displayName = value;
    }
    
    @XmlElement (name = "defaultvalue")
    @JsonProperty("defaultvalue")
    public String getDefaultValue(){
        return defaultValue;
    }

    public void setDefaultValue(String value){
        this.defaultValue = value;
    }
    
}
