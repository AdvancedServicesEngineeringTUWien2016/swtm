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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.CommandParameter;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.DeviceCommand;
import at.ac.tuwien.swtm.analytics.mainspring.client.util.CsConstants;
import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@XmlRootElement(name = "command")
public class DeviceCommandAdapter {

    private DeviceCommand command = new DeviceCommand();
    private List<CommandParameterAdapter> adapters = new LinkedList<CommandParameterAdapter>();
    private SimpleDateFormat df = new SimpleDateFormat(CsConstants.DATE_FORMAT);
    private static final Logger logger =
            Logger.getLogger(DeviceCommandAdapter.class.getName());

    public DeviceCommandAdapter() {
        df.setTimeZone(ModelHelper.getServerTimeZone());
    }

    public DeviceCommandAdapter(final DeviceCommand command) {
        df.setTimeZone(ModelHelper.getServerTimeZone());
        this.command = command;
    }

    @XmlElement(name = "name")
    public String getName() {
        return command.getName();
    }

    public void setName(String name) {
        command.setName(name);
    }

    @XmlElement(name = "adapter")
    public String getModel() {
        return command.getModel();
    }

    public void setModel(String model) {
        command.setModel(model);
    }

    @XmlElement(name = "actor")
    @JsonProperty("actor")
    public String getActorName() {
        return command.getActorName();
    }

    public void setActorName(String s) {
        command.setActorName(s);
    }

    @XmlElement(name = "status")
    public ModelHelper.CommandStatus getStatus() {
        return command.getStatus();
    }

    public void setStatus(ModelHelper.CommandStatus cs) {
        command.setStatus(cs);
    }

    @XmlElement(name = "id")
    public long getId() {
        return command.getId();
    }

    public void setId(long id) {
        command.setId(id);
    }

    @XmlElement(name = "activation_time")
    @JsonProperty("activation_time")
    public String getActivationTime() {
        return df.format(command.getActivationTime());
    }

    public void setActivationTime(String timeString) {
        try {
            Date d;
            if (timeString.equalsIgnoreCase("NOW")) {
                d = new Date();
            } else {
                d = df.parse(timeString);
            }
            command.setActivationTime(d);
        } catch (Exception ex) {
            command.setActivationTime(null);
            logger.log(Level.INFO, "Could not parse date, timestamp={0}{1}", new Object[]{timeString, ex.toString()});
        }
    }

    @XmlElement(name = "execution_time")
    @JsonProperty("execution_time")
    public String getExecutionTime() {
        return df.format(command.getExecutionTime());
    }

    public void setExecutionTime(String timeString) {
        try {
            command.setExecutionTime(df.parse(timeString));
        } catch (Exception ex) {
            command.setExecutionTime(null);
            logger.log(Level.INFO, "Could not parse date, timestamp={0}{1}", new Object[]{timeString, ex.toString()});
        }
    }

    @XmlElement(name = "parameter")
    @JsonProperty("parameter")
    public List<CommandParameterAdapter> getParameters() {
        for (final CommandParameter p : command.getParameters()) {
            adapters.add(new CommandParameterAdapter(p));
        }
        return adapters;
    }

    public void setParameters(List<CommandParameterAdapter> adapters) {
        this.adapters = adapters;
    }

    public String toString() {
        return "DeviceCommandAdapter{" + "command=" + command + ", adapters=" + adapters + ", df=" + df + '}';
    }
    
}
