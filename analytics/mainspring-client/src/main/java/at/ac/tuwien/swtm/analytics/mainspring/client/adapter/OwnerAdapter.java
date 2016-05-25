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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "owner")
public class OwnerAdapter {

    private String id;
    private String name;
    private String apikey;
    private String devicekey;
    private String socketport;

    public OwnerAdapter() {
    }

    public OwnerAdapter(final String id, final String name, final String apikey, final String devicekey, final int socketPort) {
        this.id = id;
        this.name = name;
        this.apikey = apikey;
        this.devicekey = devicekey;
        this.socketport = Integer.toString(socketPort);
    }

    public OwnerAdapter(final String id, final String name, final String apikey, final String devicekey, final String socketPort) {
        this.id = id;
        this.name = name;
        this.apikey = apikey;
        this.devicekey = devicekey;
        this.socketport = socketPort;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getDevicekey() {
        return devicekey;
    }

    public void setDevicekey(String devicekey) {
        this.devicekey = devicekey;
    }

    public short getSocketPort_short() {
        return socketport == null || socketport.isEmpty() ? 0 : Short.parseShort(socketport);
    }

    public String getSocketPort() {
        return socketport;
    }

    public void setSocketPort(String socketPort) {
        this.socketport = socketPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
