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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.EventModel;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="eventmodels")
public class EventModelListAdapter {
    private List<EventModel> models = new LinkedList<EventModel>();
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
        
    public EventModelListAdapter() {
    }
    
    public EventModelListAdapter(final List<EventModel> models) {
        this.models = models;
        if (models != null && !models.isEmpty()) {
            success = true;
        }
    }
    
    @XmlElement(name = "eventmodel")
    @JsonProperty("eventmodel")
    public List<EventModelAdapter> getModels() {
        List<EventModelAdapter> adapters = new LinkedList<EventModelAdapter>();
        for (final EventModel m : models) {
            adapters.add(new EventModelAdapter(m));
        }
        return adapters;
    }
    
}