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

public class DeviceConfig extends Device {
    
    private List<ConfigElement> configuration = new LinkedList<ConfigElement>();
    
    private List<SensorConfig> sensors = new LinkedList<SensorConfig>();
    
    private List<ActorConfig> actors = new LinkedList<ActorConfig>();
           
    public List<SensorConfig> getSensors() {
        return sensors;
    }
    
    public void setSensors(List<SensorConfig> sensors) {
        this.sensors = sensors;
    }
    
    public List<ActorConfig> getActors() {
        return actors;
    }
    
    public void setActors(List<ActorConfig> actors) {
        this.actors = actors;
    }
    
    public List<ConfigElement> getConfiguration() {
        return configuration;
    }
    
    public void setConfiguration(List<ConfigElement> configuration) {
        this.configuration = configuration;
    }
    
    public boolean isEqual(DeviceConfig d) {
        if (!super.isEqual(d)) {
            return false;
        }
        if (!(this.sensors.size() == d.sensors.size())) {
            return false;
        }
        for (int i=0; i<this.sensors.size(); i++) {
            if (!(this.sensors.get(i).isEqual(d.sensors.get(i)))) {
                return false;
            }
        }
        if (!(this.actors.size() == d.actors.size())) {
            return false;
        }
        for (int i=0; i<this.actors.size(); i++) {
            if (!(this.actors.get(i).isEqual(d.actors.get(i)))) {
                return false;
            }
        }
        if (!(this.configuration.size() == d.configuration.size())) {
            return false;
        }
        for (int i=0; i<this.configuration.size(); i++) {
            if (!(this.configuration.get(i).isEqual(d.configuration.get(i)))) {
                return false;
            }
        }
        return true;
    }
    
}
