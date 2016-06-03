package at.ac.tuwien.swtm.analytics.rest.api;

import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinRepresentation;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
@Path("wastebins")
public interface WastebinsResource {

    @GET
    @Path("{wastebinId}")
    @Produces(MediaType.APPLICATION_JSON)
    public WastebinRepresentation getWastebin(@NotNull @PathParam("wastebinId") Long wastebinId);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWastebin(WastebinRepresentation wastebinRepresentation);
}
