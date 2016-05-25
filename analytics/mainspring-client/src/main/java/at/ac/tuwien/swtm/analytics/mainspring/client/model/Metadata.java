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

public class Metadata {

    protected String name;
    protected String displayName;
    protected String defaultValue;

    public Metadata() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isEqual(Metadata m) {
        if (m == null) {
            return false;
        }
        return (this.name.equals(m.name))
                && (this.displayName.equals(m.displayName))
                && (this.defaultValue.equals(m.defaultValue));
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "Metadata{" + "name=" + name + ", displayName=" + displayName + ", defaultValue=" + defaultValue + '}';
    }

}
