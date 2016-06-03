package at.ac.tuwien.swtm.notification.webapp.rest.impl;

import at.ac.tuwien.swtm.notification.model.Notification;
import at.ac.tuwien.swtm.notification.rest.api.NotificationsResource;
import at.ac.tuwien.swtm.notification.rest.api.model.NotificationRepresentation;
import at.ac.tuwien.swtm.notification.webapp.data.NotificationDataAccess;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
        return notifications.stream().map(notification -> {
            NotificationRepresentation notificationRepresentation = new NotificationRepresentation();
            notificationRepresentation.setId(notification.getId());
            notificationRepresentation.setWastebinId(notification.getWastebinId());
            notificationRepresentation.setNotificationType(notification.getNotificationType());
            notificationRepresentation.setContent(notification.getContent());
            notificationRepresentation.setTimestamp(notification.getTimestamp());
            return notificationRepresentation;
        }).collect(Collectors.toList());
    }

}
