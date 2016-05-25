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

public class NumberCommandParameter extends CommandParameter {
    
    private double value;
    
    public double getValue() {
        return value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    public String getValueAsString() {
        long longValue = (long) value;
        if (value == (double) longValue)
            return Long.toString(longValue);
        return Double.toString(value);
    }
    
    public void setValueFromString(String s) {
        value = Double.parseDouble(s);
    }

    public String toString() {
        return "NumberCommandParameter{" + "value=" + value + '}';
    }
    
}
