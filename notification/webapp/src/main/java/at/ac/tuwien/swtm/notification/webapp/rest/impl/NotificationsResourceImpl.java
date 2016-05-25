package at.ac.tuwien.swtm.notification.webapp.rest.impl;

import at.ac.tuwien.swtm.notification.model.Notification;
import at.ac.tuwien.swtm.notification.rest.api.NotificationsResource;
import at.ac.tuwien.swtm.notification.rest.api.model.NotificationRepresentation;
import at.ac.tuwien.swtm.notification.webapp.data.NotificationDataAccess;

import javax.inject.Inject;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
public class NotificationsResourceImpl implements NotificationsResource {

    @Inject
    private NotificationDataAccess notificationDataAccess;

    @Override
    public List<NotificationRepresentation> getNotifications() {
        List<Notification> notifications = notificationDataAccess.getNotifications();
        return null;
    }

}
