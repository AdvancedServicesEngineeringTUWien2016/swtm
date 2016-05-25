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

import at.ac.tuwien.swtm.analytics.mainspring.client.model.Moment;
import at.ac.tuwien.swtm.analytics.mainspring.client.util.CsConstants;
import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="moment")
public class MomentAdapter {
    
    private Date timestamp;
    private String value;
    private SimpleDateFormat df = new SimpleDateFormat(CsConstants.DATE_FORMAT);
    private static final Logger logger =
       Logger.getLogger(MomentAdapter.class.getName());
    
    public MomentAdapter() {
        df.setTimeZone(ModelHelper.getServerTimeZone());
        timestamp = new Date();
    }
    
    public MomentAdapter(Moment moment) {
        df.setTimeZone(ModelHelper.getServerTimeZone());
        timestamp = moment.getTimestamp();
        value = moment.getValueAsString();
    }
    
    @XmlElement (name = "timestamp")
    public String getTimestamp(){
        return df.format(timestamp);
    }

    public void setTimestamp(String timestamp){

        try {
            this.timestamp = df.parse(timestamp);
        } catch (Exception ex) {
            this.timestamp = null;
            logger.log(Level.INFO, "Could not parse date, timestamp={0}{2}", new Object[]{timestamp, ex.toString()});
        }
    }
    
    @XmlElement (name = "value")
    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }
    
}
