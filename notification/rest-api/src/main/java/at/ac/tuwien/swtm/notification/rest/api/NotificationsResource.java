package at.ac.tuwien.swtm.notification.rest.api;

import at.ac.tuwien.swtm.notification.rest.api.model.NotificationRepresentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Path("notifications")
public interface NotificationsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<NotificationRepresentation> getNotifications();

}
