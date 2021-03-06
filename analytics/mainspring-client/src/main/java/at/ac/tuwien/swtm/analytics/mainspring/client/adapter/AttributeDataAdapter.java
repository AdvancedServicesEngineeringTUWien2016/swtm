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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.AttributeData;
import at.ac.tuwien.swtm.analytics.mainspring.client.model.Moment;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="attribute")
public class AttributeDataAdapter {
    private String name;
    private List<MomentAdapter> adapters = new LinkedList<MomentAdapter>();
    private RetrievalResultAdapter result;
    
    public AttributeDataAdapter() {
    }
    

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @XmlElement(name = "moment")
    @JsonProperty("moment")
    public List<MomentAdapter> getMoments() {
        return adapters;
    }
    
    public void setMoments(List<MomentAdapter> adapters) {
        this.adapters =  adapters;
    }
    
    @XmlElement(name = "result")    
    public RetrievalResultAdapter getResult() {
        return result;
    }
    
}
