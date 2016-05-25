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
package at.ac.tuwien.swtm.analytics.mainspring.client.retrieval;

import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.AttributeDataAdapter;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataAdapter;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataListAdapter;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.SensorDataAdapter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/retrieval/")
public interface RestRetriever {

    final static String DEFAULT_FROM = "2014-01-01 01:00:00.000";
    final static String DEFAULT_TO = "3000-01-01 01:00:00.000";

    @Path("devicelist/")
    @GET
    @Produces({"application/json"})
    public DeviceDataListAdapter getList(@QueryParam("apikey") String apikey,
                                         @QueryParam("device_names") String deviceNames,
                                         @Context final UriInfo uriInfo,
                                         @QueryParam("from") @DefaultValue(DEFAULT_FROM) String from,
                                         @QueryParam("to") @DefaultValue(DEFAULT_TO) String to,
                                         @QueryParam("direction") @DefaultValue("asc") String direction,
                                         @QueryParam("limit") @DefaultValue("-1") String limit,
                                         @QueryParam("count") @DefaultValue("false") String count);

    @Path("device/")
    @GET
    @Produces({"application/xml", "application/json"})
    public DeviceDataAdapter get(@QueryParam("apikey") String apikey,
                                 @QueryParam("device_name") String deviceName,
                                 @Context final UriInfo uriInfo,
                                 @QueryParam("from") @DefaultValue(DEFAULT_FROM) String from,
                                 @QueryParam("to") @DefaultValue(DEFAULT_TO) String to,
                                 @QueryParam("direction") @DefaultValue("asc") String direction,
                                 @QueryParam("limit") @DefaultValue("-1") String limit,
                                 @QueryParam("count") @DefaultValue("false") String count);

    @Path("sensor/")
    @GET
    @Produces({"application/xml", "application/json"})
    public SensorDataAdapter get(@QueryParam("apikey") String apikey,
                                 @QueryParam("device_name") String deviceName,
                                 @QueryParam("sensor_name") String sensorName,
                                 @Context final UriInfo uriInfo,
                                 @QueryParam("from") @DefaultValue(DEFAULT_FROM) String from,
                                 @QueryParam("to") @DefaultValue(DEFAULT_TO) String to,
                                 @QueryParam("direction") @DefaultValue("asc") String direction,
                                 @QueryParam("limit") @DefaultValue("-1") String limit,
                                 @QueryParam("count") @DefaultValue("false") String count);

    @Path("attribute/")
    @GET
    @Produces({"application/xml", "application/json"})
    public AttributeDataAdapter get(@QueryParam("apikey") String apikey,
                                    @QueryParam("device_name") String deviceName,
                                    @QueryParam("sensor_name") String sensorName,
                                    @QueryParam("attribute_name") String attributeName,
                                    @Context final UriInfo uriInfo,
                                    @QueryParam("from") @DefaultValue(DEFAULT_FROM) String from,
                                    @QueryParam("to") @DefaultValue(DEFAULT_TO) String to,
                                    @QueryParam("direction") @DefaultValue("asc") String direction,
                                    @QueryParam("limit") @DefaultValue("-1") String limit,
                                    @QueryParam("count") @DefaultValue("false") String count);

    @Path("devicelist/{devicemodel_name}")
    @GET
    @Produces({"application/xml", "application/json"})
    public DeviceDataListAdapter getDeviceListByDevicemodelName(@QueryParam("apikey") String apikey,
            @PathParam("devicemodel_name") String devicemodel_name,
            @Context final UriInfo uriInfo,
            @QueryParam("from") @DefaultValue(DEFAULT_FROM) String from,
            @QueryParam("to") @DefaultValue(DEFAULT_TO) String to,
            @QueryParam("direction") @DefaultValue("asc") String direction,
            @QueryParam("limit") @DefaultValue("-1") String limit,
            @QueryParam("count") @DefaultValue("false") String count);
}
