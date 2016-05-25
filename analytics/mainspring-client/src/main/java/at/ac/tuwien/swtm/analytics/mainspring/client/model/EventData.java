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

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EventData implements Serializable {

    private String name;
    private List<EventAttribute> attributes = new LinkedList<EventAttribute>();
    private String model;
    private String rawData;
    private Date timeStamp;
    private int sequenceId;

    public EventData() {
    }

    public EventData(String rawData, Date timeStamp) {
        this.rawData = rawData;
        this.timeStamp = timeStamp;
    }

    public EventData(String rawData, Date timeStamp, int sequenceId) {
        this.rawData = rawData;
        this.timeStamp = timeStamp;
        this.sequenceId = sequenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<EventAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRawData() {
        // to be added unzipping
        return rawData;
    }

    public void setRawData(String rawData) {
        // to be added zipping
        this.rawData = rawData;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public boolean isEqual(EventData e) {
        if (e == null) {
            return false;
        }
        if (!(this.model.equals(e.model))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EventData{" + "name=" + name + ", attributes=" + attributes + ", adapter=" + model + ", rawData=" + rawData + ", timeStamp=" + timeStamp + ", sequenceId=" + sequenceId + '}';
    }

}
