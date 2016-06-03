package at.ac.tuwien.swtm.notification.webapp.consumer;

import at.ac.tuwien.swtm.analytics.event.GPSConflict;
import at.ac.tuwien.swtm.notification.webapp.service.NotificationService;
import org.jboss.ejb3.annotation.ResourceAdapter;

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
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "GPSConflictEventQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class GPSConflictConsumer implements MessageListener {
    private static final Logger LOG = Logger.getLogger(GPSConflictConsumer.class.getName());

    @Inject
    private NotificationService notificationService;

    public void onMessage(Message message) {
        try {
            LOG.info("Received GPS conflict message " + message.getJMSMessageID());
            if (message instanceof ObjectMessage) {
                GPSConflict gpsConflict = (GPSConflict) ((ObjectMessage) message).getObject();
                notificationService.createLocationConflictNotification(gpsConflict);
            }
        } catch(JMSException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}
