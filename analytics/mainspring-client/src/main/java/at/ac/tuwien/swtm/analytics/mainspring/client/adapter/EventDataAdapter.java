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

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.ac.tuwien.swtm.analytics.mainspring.client.model.EventAttribute;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.EventData;
import at.ac.tuwien.swtm.analytics.mainspring.client.util.CsConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="event")
public class EventDataAdapter {
    private EventData event = new EventData();
    private List<EventAttributeAdapter> adapters = new LinkedList<EventAttributeAdapter>();
    private SimpleDateFormat df = new SimpleDateFormat(CsConstants.DATE_FORMAT);
    private static final Logger logger =
       Logger.getLogger(MomentAdapter.class.getName());
    
    public EventDataAdapter() {
    }
    
    public EventDataAdapter(final EventData event) {
        this.event = event;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return event.getName();
    }
    
    public void setName(String name) {
        event.setName(name);
    }
    
    @XmlElement(name = "rawdata")
    public String getRawData() {
        return event.getRawData();
    }
    
    public void setRawData(String rawData) {
        event.setRawData(rawData);
    }
    
    @XmlElement (name = "timestamp")
    public String getTimestamp(){
        return df.format(event.getTimeStamp());
    }

    public void setTimestamp(String timestamp){

        try {
            event.setTimeStamp(df.parse(timestamp));
        } catch (Exception ex) {
            event.setTimeStamp(null);
            logger.log(Level.INFO, "Could not parse date, timestamp={0}{2}", new Object[]{timestamp, ex.toString()});
        }
    }
    
    @XmlElement(name = "adapter")
    public String getModel() {
        return event.getModel();
    }
    
    public void setModel(String model) {
        event.setModel(model);
    }
        
    @XmlElement(name = "attribute")
    @JsonProperty("attribute")
    public List<EventAttributeAdapter> getAttributes() {
        for (final EventAttribute e : event.getAttributes()) {
            adapters.add(new EventAttributeAdapter(e));
        }
        return adapters;
    }
    
    public void setAttributes(List<EventAttributeAdapter> adapters) {
        this.adapters =  adapters;
    }
    
}
