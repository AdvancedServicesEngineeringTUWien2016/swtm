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

public class DeviceData extends Device {
    
    private List<SensorData> sensors = new LinkedList<SensorData>();

    @JsonProperty("sensor")
    public List<SensorData> getSensors() {
        return sensors;
    }
    
    public void setSensors(List<SensorData> sensors) {
        this.sensors = sensors;
    }
    
    public boolean isEqual(DeviceData d) {
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
        return true;
    }
    
}
