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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "devicemodel")
public class DeviceModelAdapter {

    private DeviceModel model = new DeviceModel();
    private List<DeviceConfigMetadataAdapter> configAdapters = new LinkedList<DeviceConfigMetadataAdapter>();
    private List<SensorAdapter> sensorAdapters = new LinkedList<SensorAdapter>();
    private List<ActorAdapter> actorAdapters = new LinkedList<ActorAdapter>();
    private List<ScriptConfigAdapter> scriptConfigAdapters = new LinkedList<ScriptConfigAdapter>();
    private List<SensorModelAdapter> sensorModelAdapters = null; // optional
    private List<ActorModelAdapter> actorModelAdapters = null; // optional

    public DeviceModelAdapter() {
    }

    public DeviceModelAdapter(final DeviceModel model) {
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
    public List<DeviceConfigMetadataAdapter> getConfiguration() {
        for (final DeviceConfigMetadata dcm : model.getConfiguration()) {
            configAdapters.add(new DeviceConfigMetadataAdapter(dcm));
        }
        return configAdapters;
    }

    public void setConfiguration(List<DeviceConfigMetadataAdapter> adapters) {
        this.configAdapters = adapters;
    }

    @XmlElement(name = "sensor")
    @JsonProperty("sensor")
    public List<SensorAdapter> getSensors() {
        for (final Sensor s : model.getSensors()) {
            sensorAdapters.add(new SensorAdapter(s));
        }
        return sensorAdapters;
    }

    public void setSensors(List<SensorAdapter> adapters) {
        this.sensorAdapters = adapters;
    }

    @XmlElement(name = "actor")
    @JsonProperty("actor")
    public List<ActorAdapter> getActors() {
        for (final Actor a : model.getActors()) {
            actorAdapters.add(new ActorAdapter(a));
        }
        return actorAdapters;
    }

    public void setActors(List<ActorAdapter> adapters) {
        this.actorAdapters = adapters;
    }

    @XmlElement(name = "script")
    public List<ScriptConfigAdapter> getScript() {
        for (final ScriptConfig sc : model.getScripts()) {
            scriptConfigAdapters.add(new ScriptConfigAdapter(sc));
        }
        return scriptConfigAdapters;
    }

    public void setScript(List<ScriptConfigAdapter> adapters) {
        this.scriptConfigAdapters = adapters;
    }

    @XmlElement(name = "sensorModel")
    @JsonProperty("sensorModel")
    public List<SensorModelAdapter> getSensorModelAdapters() {
        return sensorModelAdapters;
    }

    public void setSensorModelAdapters(List<SensorModelAdapter> sensorModelAdapters) {
        this.sensorModelAdapters = sensorModelAdapters;
    }

    @XmlElement(name = "actorModel")
    @JsonProperty("actorModel")
    public List<ActorModelAdapter> getActorModelAdapters() {
        return actorModelAdapters;
    }

    public void setActorModelAdapters(List<ActorModelAdapter> actorModelAdapters) {
        this.actorModelAdapters = actorModelAdapters;
    }
}
