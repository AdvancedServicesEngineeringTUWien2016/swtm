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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.EventData;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "events")
public class EventDataListAdapter {

    protected List<EventData> events = new LinkedList<EventData>();
    private RetrievalResultAdapter retrievalResultAdapter;

    public EventDataListAdapter() {
    }

    public EventDataListAdapter(final RetrievalResult retrievalResult, final List<EventData> events) {
        this.events = events;
        this.retrievalResultAdapter = new RetrievalResultAdapter(retrievalResult);
    }

    @XmlElement(name = "result")
    @JsonProperty("result")
    public RetrievalResultAdapter getRetrievalResultAdapter() {
        return retrievalResultAdapter;
    }

    public void setRetrievalResultAdapter(RetrievalResultAdapter retrievalResult) {
        this.retrievalResultAdapter = retrievalResult;
    }

    @XmlElement(name = "event")
    @JsonProperty("event")
    public List<EventDataAdapter> getEvents() {
        List<EventDataAdapter> adapters = new LinkedList<EventDataAdapter>();
        for (final EventData e : events) {
            adapters.add(new EventDataAdapter(e));
        }
        return adapters;
    }

}
