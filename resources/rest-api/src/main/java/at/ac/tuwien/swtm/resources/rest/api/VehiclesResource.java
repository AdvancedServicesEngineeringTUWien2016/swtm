package at.ac.tuwien.swtm.resources.rest.api;

import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
@Path("resources")
public interface VehiclesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<VehicleRepresentation> getVehicles();

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putVehicle(VehicleRepresentation vehicleRepresentation);
}
