package at.ac.tuwien.swtm.notification.webapp.service;

import at.ac.tuwien.swtm.analytics.event.AbstractSensorFailure;
import at.ac.tuwien.swtm.analytics.event.GPSConflict;
import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.analytics.event.SensorType;
import at.ac.tuwien.swtm.notification.model.Notification;
import at.ac.tuwien.swtm.notification.model.NotificationType;
import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Stateless
public class NotificationService {

    @Inject
    private EntityManager em;

    @Inject
    private CriteriaBuilderFactory cbf;

    public void createLocationConflictNotification(GPSConflict gpsConflict) {
        if (!existsNotification(gpsConflict, NotificationType.GPS_CONFLICT)) {
            Notification notification = createNotification(gpsConflict, "Wastebin [" + gpsConflict.getWastebinName() + "(id=" + gpsConflict.getWastebinId() + ")]: GPS conflict", NotificationType.GPS_CONFLICT);
            em.persist(notification);
        }
    }

    public void createSensorFailureNotification(SensorFailure sensorFailure) {
        NotificationType notificationType = getFailureNotificationTypeFromSensorType(sensorFailure.getSensorType());
        if (!existsNotification(sensorFailure, notificationType)) {
            Notification notification = createNotification(sensorFailure, "Wastebin [" + sensorFailure.getWastebinName() + "(id=" + sensorFailure.getWastebinId() + ")]: sensor error: " + sensorFailure.getSensorType(), notificationType);
            em.persist(notification);
        }
    }

    private boolean existsNotification(AbstractSensorFailure sensorFailure, NotificationType notificationType) {
        CriteriaBuilder<Boolean> cb = cbf.create(em, Boolean.class)
                .from(Notification.class)
                .select("CASE WHEN COUNT(*) > 0 THEN true ELSE false END")
                .where("wastebinId").eq(sensorFailure.getWastebinId())
                .where("notificationType").eq(notificationType);

        if (sensorFailure.getLastValidMomentTimestamp() != null) {
            cb.where("timestamp").ge(sensorFailure.getLastValidMomentTimestamp());
        }
        return cb.getSingleResult();
    }

    private Notification createNotification(AbstractSensorFailure sensorFailure, String content, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setTimestamp(sensorFailure.getTimestamp());
        notification.setWastebinId(sensorFailure.getWastebinId());
        notification.setNotificationType(notificationType);

        return notification;
    }

    private NotificationType getFailureNotificationTypeFromSensorType(SensorType sensorType) {
        switch (sensorType) {
            case FILLING_DEGREE: return NotificationType.FILLING_DEGREE_FAILURE;
            case GPS: return NotificationType.GPS_FAILURE;
            case PAYLOAD: return NotificationType.PAYLOAD_FAILURE;
            default: throw new IllegalStateException("No valid sensor type: " + sensorType);
        }
    }

}
