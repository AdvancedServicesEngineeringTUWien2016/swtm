package at.ac.tuwien.swtm.resources.webapp.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class ResourcesEntityManagerProducer {

    @Produces
    @PersistenceUnit(unitName = "resourcesPU")
    private EntityManagerFactory emf;

    @Produces
    @PersistenceContext(unitName = "resourcesPU")
    private EntityManager em;

}
