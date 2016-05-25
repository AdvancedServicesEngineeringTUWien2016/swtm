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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.SensorModel;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="sensormodels")
public class SensorModelListAdapter {
    private List<SensorModel> models = new LinkedList<SensorModel>();
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
        
    public SensorModelListAdapter() {
    }
    
    public SensorModelListAdapter(final List<SensorModel> models) {
        this.models = models;
        if (models != null && !models.isEmpty()) {
            success = true;
        }
    }
    
    @XmlElement(name = "sensormodel")
    @JsonProperty("sensormodel")
    public List<SensorModelAdapter> getModels() {
        List<SensorModelAdapter> adapters = new LinkedList<SensorModelAdapter>();
        for (final SensorModel m : models) {
            adapters.add(new SensorModelAdapter(m));
        }
        return adapters;
    }
    
}
