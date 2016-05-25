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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.ConfigElement;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.SensorConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="sensor")
public class SensorConfigAdapter {
    protected SensorConfig sensor = new SensorConfig();
    private List<ConfigElementAdapter> configAdapters = new LinkedList<ConfigElementAdapter>();
    
    public SensorConfigAdapter() {
    }
    
    public SensorConfigAdapter(final SensorConfig sensor) {
        this.sensor = sensor;
    }
    
    public SensorConfig resolveEntity() {
        for (final ConfigElementAdapter adapter : configAdapters) {
            ConfigElement ce = adapter.resolveEntity();
            sensor.getConfiguration().add(ce);
        }
        return sensor;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return sensor.getName();
    }
    
    public void setName(String name) {
        sensor.setName(name);
    }
    
    @XmlElement(name = "adapter")
    public String getModel() {
        return sensor.getModel();
    }
    
    public void setModel(String model) {
        sensor.setModel(model);
    }
        
    @XmlElement(name = "config")
    @JsonProperty("config")
    public List<ConfigElementAdapter> getConfiguration() {
        for (final ConfigElement ce : sensor.getConfiguration()) {
            configAdapters.add(new ConfigElementAdapter(ce));
        }
        return configAdapters;
    }
    
    public void setConfiguration(List<ConfigElementAdapter> adapters) {
        this.configAdapters =  adapters;
    }
    
}
