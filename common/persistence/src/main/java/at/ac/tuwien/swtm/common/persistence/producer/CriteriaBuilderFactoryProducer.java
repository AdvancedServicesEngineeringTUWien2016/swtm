package at.ac.tuwien.swtm.common.persistence.producer;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class CriteriaBuilderFactoryProducer {

    @Inject
    private EntityManagerFactory emf;
    @Inject
    private Event<CriteriaBuilderConfiguration> configEvent;

    private CriteriaBuilderFactory criteriaBuilderFactory;

    @PostConstruct
    public void init() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        configEvent.fire(config);
        this.criteriaBuilderFactory = config.createCriteriaBuilderFactory(emf);
    }

    @Produces
    public CriteriaBuilderFactory getCriteriaBuilderFactory() {
        return criteriaBuilderFactory;
    }

}
