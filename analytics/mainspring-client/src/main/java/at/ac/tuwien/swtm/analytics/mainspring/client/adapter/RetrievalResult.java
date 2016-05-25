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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/** contain the metadata for a moment or command search operation
 * 
 */
public class RetrievalResult {
    
    private int count = 0;
    private Date nextTs = null;
    private boolean limitReached = false;
    
    /**
     * 
     * @return the numbers of elements found
     */
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * 
     * @return the next timestamp to be used in a search operation to get consecutive results if a limit was reached
     */
    public Date getNextTs() {
        return nextTs;
    }
    
    public void setNextTs(Date nextTs) {
        this.nextTs = nextTs;
    }
    
    /**
     * 
     * @return maximum number of elements returned
     */
    public boolean getLimitReached() {
        return limitReached;
    }
    
    public void setLimitReached(boolean limitReached) {
        this.limitReached = limitReached;
    }
    
}
