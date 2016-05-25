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

public abstract class Device {
    
    protected String name;
    protected String model;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    } 
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public boolean isEqual(Device d) {
        if (d == null) {
            return false;
        }
        if (!(this.name.equals(d.name))) {
            return false;
        }
        if (!(this.model.equals(d.model))) {
            return false;
        }
        return true;
    }
}
