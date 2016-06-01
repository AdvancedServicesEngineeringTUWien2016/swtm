package at.ac.tuwien.swtm.planner.rest.api;

import at.ac.tuwien.swtm.planner.rest.api.model.PlanRepresentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
@Path("plans")
public interface PlansResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlanRepresentation createNewPlan();

}
