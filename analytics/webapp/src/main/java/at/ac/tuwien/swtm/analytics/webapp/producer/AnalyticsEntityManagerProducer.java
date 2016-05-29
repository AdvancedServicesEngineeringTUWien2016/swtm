package at.ac.tuwien.swtm.analytics.webapp.producer;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class AnalyticsEntityManagerProducer {

    @Produces
    @PersistenceUnit(unitName = "analyticsPU")
    private EntityManagerFactory emf;

    @Produces
    @PersistenceContext(unitName = "analyticsPU")
    private EntityManager em;

}
