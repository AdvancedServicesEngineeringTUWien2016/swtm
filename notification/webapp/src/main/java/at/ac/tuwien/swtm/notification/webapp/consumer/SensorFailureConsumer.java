package at.ac.tuwien.swtm.notification.webapp.consumer;

import at.ac.tuwien.swtm.analytics.event.GPSConflict;
import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.notification.webapp.service.NotificationService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.logging.Logger;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "SensorFailureEventQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class SensorFailureConsumer {
    private static final Logger LOG = Logger.getLogger(SensorFailureConsumer.class.getName());

    @Inject
    private NotificationService notificationService;

    public void onMessage(Message message) throws JMSException {
        LOG.info("Received sensor failure message " + message.getJMSMessageID());
        if (message instanceof ObjectMessage) {
            SensorFailure sensorFailure = (SensorFailure) ((ObjectMessage) message).getObject();
            notificationService.createSensorFailureNotification(sensorFailure.getWastebinId(), sensorFailure.getSensorType());
        }
    }
}
