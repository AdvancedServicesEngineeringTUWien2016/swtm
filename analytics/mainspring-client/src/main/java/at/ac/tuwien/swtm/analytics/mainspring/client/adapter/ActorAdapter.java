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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.Actor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="actor")
public class ActorAdapter {
    protected Actor actor = new Actor();
    
    public ActorAdapter() {
    }
    
    public ActorAdapter(final Actor actor) {
        this.actor = actor;
    }
    
    public Actor resolveEntity() {
        return actor;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return actor.getName();
    }
    
    public void setName(String name) {
        actor.setName(name);
    }
    
    @XmlElement(name = "adapter")
    public String getModel() {
        return actor.getModel();
    }
    
    public void setModel(String model) {
        actor.setModel(model);
    }
    
}
