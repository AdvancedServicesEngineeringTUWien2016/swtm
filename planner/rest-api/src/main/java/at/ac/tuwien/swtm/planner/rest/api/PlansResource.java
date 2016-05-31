package at.ac.tuwien.swtm.planner.rest.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
@Path("plans")
public interface PlansResource {

    @POST
    public Response createNewPlan();

}
