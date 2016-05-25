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

import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.RetrievalResult;
import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class AttributeData {
    
    private String name;
    private ModelHelper.Datatype type;
    private List<Moment> moments = new LinkedList<Moment>();
    private RetrievalResult result = new RetrievalResult();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ModelHelper.Datatype getType() {
        return type;
    }
    
    public void setType(ModelHelper.Datatype type) {
        this.type = type;
    }

    public List<Moment> getMoments() {
        return moments;
    }
    
    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }
    
    public RetrievalResult getResult() {
        return result;
    }
    
    public void setResult(RetrievalResult result) {
        this.result = result;
    }
    
    public boolean isEqual(AttributeData a) {
        if (a == null)
            return false;
        return ((this.name.equals(a.name)) && (this.type == a.type));
    }
    
}
