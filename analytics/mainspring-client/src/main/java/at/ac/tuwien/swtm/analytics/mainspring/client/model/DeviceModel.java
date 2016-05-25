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

public class DeviceModel {
    
    private String name;
    
    private List<Sensor> sensors = new LinkedList<Sensor>();
    private List<Actor> actors = new LinkedList<Actor>();
    private List<DeviceConfigMetadata> configuration = new LinkedList<DeviceConfigMetadata>();
    private List<ScriptConfig> scripts = new LinkedList<ScriptConfig>();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Sensor> getSensors() {
        return sensors;
    }
    
    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
    
    public List<Actor> getActors() {
        return actors;
    }
    
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    
    public List<DeviceConfigMetadata> getConfiguration() {
        return configuration;
    }
    
    public List<ScriptConfig> getScripts() {
        return scripts;
    }
    
    public void setScripts(List<ScriptConfig> scripts) {
        this.scripts = scripts;
    }
    
    public void setConfiguration(List<DeviceConfigMetadata> configuration) {
        this.configuration = configuration;
    }
    
    public DeviceConfigMetadata readConfigItem (String item) {
        for (DeviceConfigMetadata dcm : configuration) {
            if (dcm.getName().equals(item)) {
                return dcm;
            }
        }
        return null;
    }
    
    public boolean isEqual(DeviceModel dm) {
        if (dm == null) {
            return false;
        }
        if (!(this.name.equals(dm.name))) {
            return false;
        }
        if (!(this.sensors.size() == dm.sensors.size())) {
            return false;
        }
        for (int i=0; i<this.sensors.size(); i++) {
            if (!(this.sensors.get(i).isEqual(dm.sensors.get(i)))) {
                return false;
            }
        }
        if (!(this.actors.size() == dm.actors.size())) {
            return false;
        }
        for (int i=0; i<this.actors.size(); i++) {
            if (!(this.actors.get(i).isEqual(dm.actors.get(i)))) {
                return false;
            }
        }
        if (!(this.configuration.size() == dm.configuration.size())) {
            return false;
        }
        for (int i=0; i<this.configuration.size(); i++) {
            if (!(this.configuration.get(i).isEqual(dm.configuration.get(i)))) {
                return false;
            }
        }
        if (!(this.scripts.size() == dm.scripts.size())) {
            return false;
        }
        for (int i=0; i<this.scripts.size(); i++) {
            if (!(this.scripts.get(i).isEqual(dm.scripts.get(i)))) {
                return false;
            }
        }
        return true;
    }
    
}
