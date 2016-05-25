package at.ac.tuwien.swtm.notification.webapp.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
public class NotificationEntityManagerProducer {

    @Produces
    @PersistenceContext(unitName = "notificationPU")
    private EntityManager em;

}
