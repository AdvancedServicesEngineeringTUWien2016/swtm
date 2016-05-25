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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.ActorConfig;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.ConfigElement;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.DeviceConfig;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.SensorConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name="device")
public class DeviceConfigAdapter {
    private DeviceConfig device = new DeviceConfig();
    private List<ConfigElementAdapter> configAdapters = new LinkedList<ConfigElementAdapter>();
    private List<SensorConfigAdapter> sensors = new LinkedList<SensorConfigAdapter>();
    private List<ActorConfigAdapter> actors = new LinkedList<ActorConfigAdapter>();
    
    public DeviceConfigAdapter() {
    }
    
    public DeviceConfigAdapter(final DeviceConfig device) {
        this.device = device;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return device.getName();
    }
    
    public void setName(String name) {
        device.setName(name);
    }
    
    @XmlElement(name = "adapter")
    public String getModel() {
        return device.getModel();
    }
    
    public void setModel(String model) {
        device.setModel(model);
    }
    
    @XmlElement(name = "config")
    @JsonProperty("config")
    public List<ConfigElementAdapter> getConfiguration() {
        for (final ConfigElement ce : device.getConfiguration()) {
            configAdapters.add(new ConfigElementAdapter(ce));
        }
        return configAdapters;
    }
    
    public void setConfiguration(List<ConfigElementAdapter> adapters) {
        this.configAdapters =  adapters;
    }
    
    @XmlElement(name = "sensor")
    @JsonProperty("sensor")
    public List<SensorConfigAdapter> getSensors() {
        for (final SensorConfig sc : device.getSensors()) {
            sensors.add(new SensorConfigAdapter(sc));
        }
        return sensors;
    }
    
    public void setSensors(List<SensorConfigAdapter> adapters) {
        this.sensors =  adapters;
    }
    
    @XmlElement(name = "actor")
    @JsonProperty("actor")
    public List<ActorConfigAdapter> getActors() {
        for (final ActorConfig ac : device.getActors()) {
            actors.add(new ActorConfigAdapter(ac));
        }
        return actors;
    }
    
    public void setActors(List<ActorConfigAdapter> adapters) {
        this.actors =  adapters;
    }
    
    
    
}
