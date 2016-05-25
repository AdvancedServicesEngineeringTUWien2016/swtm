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

import java.util.LinkedList;
import java.util.List;

public class SensorModel {
    
    private String name;
    
    private List<AttributeMetadata> attributes = new LinkedList<AttributeMetadata>();
    private List<SensorConfigMetadata> configuration = new LinkedList<SensorConfigMetadata>();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<AttributeMetadata> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<AttributeMetadata> attributeMetadata) {
        this.attributes = attributeMetadata;
    }
    
    public List<SensorConfigMetadata> getConfiguration() {
        return configuration;
    }
    
    public void setConfiguration(List<SensorConfigMetadata> configuration) {
        this.configuration = configuration;
    }
    
    public SensorConfigMetadata readConfigItem (String item) {
        for (SensorConfigMetadata acm : configuration) {
            if (acm.getName().equals(item)) {
                return acm;
            }
        }
        return null;
    }
    
    public boolean isEqual(SensorModel s) {
        if (s == null) {
            return false;
        }
        if (!(this.name.equals(s.name))) {
            return false;
        }
        if (!(this.attributes.size() == s.attributes.size())) {
            return false;
        }
        for (int i=0; i<this.attributes.size(); i++) {
            if (!(this.attributes.get(i).isEqual(s.attributes.get(i)))) {
                return false;
            }
        }
        if (!(this.configuration.size() == s.configuration.size())) {
            return false;
        }
        for (int i=0; i<this.configuration.size(); i++) {
            if (!(this.configuration.get(i).isEqual(s.configuration.get(i)))) {
                return false;
            }
        }
        return true;
    }
    
}
