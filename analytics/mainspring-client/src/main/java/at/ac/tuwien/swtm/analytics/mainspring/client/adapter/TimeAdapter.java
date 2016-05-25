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

import at.ac.tuwien.swtm.analytics.mainspring.client.util.CsConstants;
import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement(name="time")
public class TimeAdapter {
    
    private Date timestamp;
    private SimpleDateFormat df = new SimpleDateFormat(CsConstants.DATE_FORMAT+"Z");
    
    public TimeAdapter() {
        df.setTimeZone(ModelHelper.getServerTimeZone());
    }
    
    public TimeAdapter(Date timestamp) {
        df.setTimeZone(ModelHelper.getServerTimeZone());
        this.timestamp = timestamp;
    }
       
    @XmlElement
    public String getTime(){
        return df.format(timestamp);
    }
    
}
