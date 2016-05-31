package at.ac.tuwien.swtm.analytics.rest.api;

import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinMomentRepresentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 31.05.2016.
 */
@Path("wastebinMoments")
public interface WastebinMomentResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WastebinMomentRepresentation> getLatestMoments();
}
