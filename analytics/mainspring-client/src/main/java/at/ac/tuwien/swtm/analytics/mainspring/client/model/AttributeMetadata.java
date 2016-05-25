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

public class AttributeMetadata extends Metadata {

    private ModelHelper.Datatype type;

    public ModelHelper.Datatype getType() {
        return type;
    }

    public void setType(ModelHelper.Datatype type) {
        this.type = type;
    }

    public boolean isEqual(AttributeMetadata a) {
        if (a == null) {
            return false;
        }
        if (!super.equals(a)) {
            return false;
        }
        return (this.type == a.type);
    }

    @Override
    public String toString() {
        return "AttributeMetadata{" + "type=" + type + ", name=" + name + ", displayName=" + displayName + ", defaultValue=" + defaultValue + '}';
    }
}
