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


import at.ac.tuwien.swtm.analytics.mainspring.client.util.ModelHelper;

public class EventFilter {

    private String attribute;
    private ModelHelper.EventFilterOperator operator;
    private String compareValue;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public ModelHelper.EventFilterOperator getOperator() {
        return operator;
    }

    public void setOperator(ModelHelper.EventFilterOperator operator) {
        this.operator = operator;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    @Override
    public String toString() {
        return "EventFilter{" + "attribute=" + attribute + ", operator=" + operator + ", compareValue=" + compareValue + '}';
    }

}
