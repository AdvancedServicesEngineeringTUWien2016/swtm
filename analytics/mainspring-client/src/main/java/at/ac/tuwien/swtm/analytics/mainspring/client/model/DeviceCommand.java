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

import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DeviceCommand extends Device {
    
    private String actorName;

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
    
    private Date executionTime = new Date(0);

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    private Date activationTime;

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private ModelHelper.CommandStatus status = ModelHelper.CommandStatus.New;

    public ModelHelper.CommandStatus getStatus() {
        return status;
    }

    public void setStatus(ModelHelper.CommandStatus status) {
        this.status = status;
    }

    private List<CommandParameter> parameters = new LinkedList<CommandParameter>();
        
    public List<CommandParameter> getParameters() {
        return parameters;
    }
    
    public void setParameters(List<CommandParameter> parameters) {
        this.parameters = parameters;
    }
    
    public boolean isEqual(DeviceCommand d) {
        if (!super.isEqual(d)) {
            return false;
        }
        if (!(this.id == d.id)) {
            return false;
        }
        if (!(this.activationTime.getTime() == d.activationTime.getTime())) {
            return false;
        }
        if (!(this.executionTime.getTime() == d.executionTime.getTime())) {
            return false;
        }
        if (!(this.parameters.size() == d.parameters.size())) {
            return false;
        }
        for (int i=0; i<this.parameters.size(); i++) {
            if (!(this.parameters.get(i).getValueAsString().equals(d.parameters.get(i).getValueAsString()))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "DeviceCommand{" + "actorName=" + actorName + ", executionTime=" + executionTime + ", activationTime=" + activationTime + ", id=" + id + ", status=" + status + ", parameters=" + parameters + '}';
    }
    
}
