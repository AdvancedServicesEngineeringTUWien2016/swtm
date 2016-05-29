package at.ac.tuwien.swtm.resources.webapp.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class ResourcesEntityManagerProducer {

    @Produces
    @PersistenceContext(unitName = "resourcesPU")
    private EntityManager em;

}
