package at.ac.tuwien.swtm.resources.rest.api;

import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Response putVehicle(VehicleRepresentation vehicleRepresentation);
}
