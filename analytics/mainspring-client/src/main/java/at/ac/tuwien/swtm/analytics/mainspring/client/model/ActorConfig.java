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

import java.util.LinkedList;
import java.util.List;

public class ActorConfig extends Actor {
       
    private List<ConfigElement> configuration = new LinkedList<ConfigElement>();
       
    public List<ConfigElement> getConfiguration() {
        return configuration;
    }
    
    public void setConfiguration(List<ConfigElement> configuration) {
        this.configuration = configuration;
    }
    
    public boolean isEqual(ActorConfig ac) {
        if (!super.isEqual(ac)) {
            return false;
        }
        if (!(this.configuration.size() == ac.configuration.size())) {
            return false;
        }
        for (int i=0; i<this.configuration.size(); i++) {
            if (!(this.configuration.get(i).isEqual(ac.configuration.get(i)))) {
                return false;
            }
        }
        return true;
    }
    
}
