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

public class GpsDataCommandParameter extends CommandParameter {
    
    private GpsData gpsData = new GpsData();

    public GpsData getGpsData() {
        return gpsData;
    }
    
    public void setGpsData(GpsData gpsData) {
        this.gpsData = gpsData;
    }
    
    public String getValueAsString() {
        return ""+gpsData.getLongtuide()+"|"+gpsData.getLatitude()+"|"+gpsData.getAltitude();
    }
    
    public void setValueFromString(String s) {
        String[] gpsDataAsString = s.split("\\|");
        gpsData.setLongitude(Double.parseDouble(gpsDataAsString[0]));
        gpsData.setLatitude(Double.parseDouble(gpsDataAsString[1]));
        gpsData.setAltitude(Double.parseDouble(gpsDataAsString[2]));
    }
    
}
