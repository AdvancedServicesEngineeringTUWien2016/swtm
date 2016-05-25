package at.ac.tuwien.swtm.notification.webapp.consumer;

import at.ac.tuwien.swtm.notification.webapp.service.NotificationService;
import org.jboss.ejb3.annotation.ResourceAdapter;

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
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "notificationQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
@ResourceAdapter("artemis-ra-1.1.0.wildfly-011.jar")
public class EventConsumer {
    private static final Logger LOG = Logger.getLogger(EventConsumer.class.getName());

    @Inject
    private NotificationService notificationService;

    public void onMessage(Message message) throws JMSException {
        LOG.info("Received JMS message " + message.getJMSMessageID());
        if (message instanceof ObjectMessage) {
            ((ObjectMessage) message).getObject();
        }
    }
}
