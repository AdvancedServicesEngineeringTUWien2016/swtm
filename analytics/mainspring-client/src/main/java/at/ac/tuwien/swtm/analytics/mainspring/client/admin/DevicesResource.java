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
package at.ac.tuwien.swtm.analytics.mainspring.client.admin;

import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataListAdapterExt;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/admin/devices/")
public interface DevicesResource {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    DeviceDataListAdapterExt get(@QueryParam("apikey") String apikey,
                                        @QueryParam("from") @DefaultValue("") String from,
                                        @QueryParam("to") @DefaultValue("") String to,
                                        @QueryParam("limit") @DefaultValue("15") String limit,
                                        @Context final UriInfo uriInfo);
}
