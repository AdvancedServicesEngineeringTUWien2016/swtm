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

import at.ac.tuwien.swtm.analytics.mainspring.client.util.CsConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateCommandParameter extends CommandParameter {
    
    private Date value;
    private SimpleDateFormat df = new SimpleDateFormat(CsConstants.DATE_FORMAT);
    
    private static final Logger logger =
       Logger.getLogger(DateCommandParameter.class.getName());
    
    public Date getValue() {
        return value;
    }
    
    public void setValue(Date value) {
        this.value = value;
    }
    
    public String getValueAsString() {
        return df.format(value);
    }
    
    public void setValueFromString (String s) {
        try {
            value = df.parse(s);
        } catch (java.text.ParseException ex) {
            logger.log(Level.WARNING, "Exception in parsing date value{0}", ex.toString());
        }
        
    }
    
}
