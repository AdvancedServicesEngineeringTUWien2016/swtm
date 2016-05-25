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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class SensorData extends Sensor {
       
    private List<AttributeData> attributes = new LinkedList<AttributeData>();

    public List<AttributeData> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<AttributeData> attributes) {
        this.attributes = attributes;
    }
        
    public boolean isEqual(SensorData s) {
        if (!super.isEqual(s)) {
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
        return true;
    }
    
}
