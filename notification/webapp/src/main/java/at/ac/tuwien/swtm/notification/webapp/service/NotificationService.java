package at.ac.tuwien.swtm.notification.webapp.service;

import at.ac.tuwien.swtm.analytics.event.SensorType;
import at.ac.tuwien.swtm.notification.model.Notification;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Stateless
public class NotificationService {

    @Inject
    private EntityManager em;

    public void createLocationConflictNotification(Long wastebinId) {
        Notification notification = new Notification();
        notification.setContent("Container " + wastebinId + ": Konflikt bei Positionszuordnung");
        em.persist(notification);
    }

    public void createSensorFailureNotification(Long wastebinId, SensorType sensorType) {
        Notification notification = new Notification();
        notification.setContent("Container " + wastebinId + ": Sensor-Fehler: " + sensorType);
        em.persist(notification);
    }

}
