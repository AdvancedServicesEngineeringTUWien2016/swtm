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

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.ac.tuwien.swtm.analytics.mainspring.client.model.DeviceData;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.SensorData;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="device")
public class DeviceDataAdapter {
    private String name;
    private String model;
    private List<SensorDataAdapter> sensorAdapters = new LinkedList<SensorDataAdapter>();
    
    public DeviceDataAdapter() {
    }
    

    @XmlElement(name = "name")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @XmlElement(name = "model")
    @JsonProperty("model")
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
        
    @XmlElement(name = "sensor")
    @JsonProperty("sensor")
    public List<SensorDataAdapter> getSensors() {
        return sensorAdapters;
    }
    
    public void setSensors(List<SensorDataAdapter> adapters) {
        this.sensorAdapters =  adapters;
    }
        
}
