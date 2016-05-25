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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.ActorConfigMetadata;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.ActorModel;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.AttributeMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="actormodel")
public class ActorModelAdapter {
    private ActorModel model = new ActorModel();
    private List<ActorConfigMetadataAdapter> configAdapters = new LinkedList<ActorConfigMetadataAdapter>();
    private List<AttributeMetadataAdapter> attributeAdapters = new LinkedList<AttributeMetadataAdapter>();
    
    public ActorModelAdapter() {
    }
    
    public ActorModelAdapter(final ActorModel model) {
        this.model = model;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return model.getName();
    }
    
    public void setName(String name) {
        model.setName(name);
    }
    
    @XmlElement(name = "config")
    @JsonProperty("config")
    public List<ActorConfigMetadataAdapter> getConfiguration() {
        for (final ActorConfigMetadata s : model.getConfiguration()) {
            configAdapters.add(new ActorConfigMetadataAdapter(s));
        }
        return configAdapters;
    }
    
    public void setConfiguration(List<ActorConfigMetadataAdapter> adapters) {
        this.configAdapters =  adapters;
    }
    
    @XmlElement(name = "attribute")
    @JsonProperty("attribute")
    public List<AttributeMetadataAdapter> getAttributes() {
        for (final AttributeMetadata a : model.getAttributes()) {
            attributeAdapters.add(new AttributeMetadataAdapter(a));
        }
        return attributeAdapters;
    }
    
    public void setAttributes(List<AttributeMetadataAdapter> adapters) {
        this.attributeAdapters =  adapters;
    }
    
}
