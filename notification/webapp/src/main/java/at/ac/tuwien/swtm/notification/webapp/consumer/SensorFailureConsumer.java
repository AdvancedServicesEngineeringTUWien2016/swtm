package at.ac.tuwien.swtm.notification.webapp.consumer;

import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.notification.webapp.service.NotificationService;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.logging.Level;
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
public class SensorFailureConsumer implements MessageListener {
    private static final Logger LOG = Logger.getLogger(SensorFailureConsumer.class.getName());

    @Inject
    private NotificationService notificationService;

    public void onMessage(Message message) {
        try {
            LOG.info("Received sensor failure message " + message.getJMSMessageID());
            if (message instanceof ActiveMQObjectMessage) {
                SensorFailure sensorFailure = (SensorFailure) ((ActiveMQObjectMessage) message).getObject();
                notificationService.createSensorFailureNotification(sensorFailure);
            }
        } catch(JMSException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}
