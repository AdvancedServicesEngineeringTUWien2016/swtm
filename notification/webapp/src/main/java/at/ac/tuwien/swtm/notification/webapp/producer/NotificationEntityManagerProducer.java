package at.ac.tuwien.swtm.notification.webapp.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
public class NotificationEntityManagerProducer {

    @Produces
    @PersistenceUnit(unitName = "notificationPU")
    private EntityManagerFactory emf;

    @Produces
    @PersistenceContext(unitName = "notificationPU")
    private EntityManager em;

}
