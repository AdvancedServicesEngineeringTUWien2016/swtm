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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.ac.tuwien.swtm.analytics.mainspring.client.util.CsConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="result")
public class RetrievalResultAdapter {
    
    private int count = 0;
    private Date nextTs = null;
    private boolean limitReached = false;
    private SimpleDateFormat df = new SimpleDateFormat(CsConstants.DATE_FORMAT);
    private static final Logger logger =
       Logger.getLogger(RetrievalResultAdapter.class.getName());

    
    public RetrievalResultAdapter() {
    }
    
    public RetrievalResultAdapter(RetrievalResult result) {
        count = result.getCount();
        nextTs = result.getNextTs();
        limitReached = result.getLimitReached();
    }
    
    @XmlElement (name = "count")
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    @XmlElement (name = "next_timestamp")
    @JsonProperty("next_timestamp")
    public String getNextTs() {
        if (nextTs == null)
            return "";
        else
            return df.format(nextTs);
    }
    
    public void setNextTs(String nextTs) {
        try {
            this.nextTs = df.parse(nextTs);
        } catch (Exception ex) {
            this.nextTs = null;
            logger.log(Level.INFO, "Could not parse date, timestamp={0}{2}", new Object[]{nextTs, ex.toString()});
        }
    }
    
    @XmlElement (name = "limit_reached")
    @JsonProperty("limit_reached")
    public boolean getLimitReached() {
        return limitReached;
    }
    
    public void setLimitReached(boolean limitReached) {
        this.limitReached = limitReached;
    }
    
}
