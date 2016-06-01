package at.ac.tuwien.swtm.resources.webapp;

import at.ac.tuwien.swtm.common.model.Point;
import at.ac.tuwien.swtm.resources.model.Vehicle;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 31.05.2016.
 */
@ApplicationScoped
public class DbInitializer {
    private static final Logger LOG = Logger.getLogger(DbInitializer.class.getName());

    @Resource
    private UserTransaction transaction;

    @Inject
    private EntityManager em;

    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            final double latitude = 48.405037;
            final double longitude = 15.675021;
            transaction.begin();
            Vehicle v1 = createVehicle("Vehicle 1", 20, 100, latitude, longitude);
            Vehicle v2 = createVehicle("Vehicle 2", 15, 110, latitude, longitude);
            Vehicle v3 = createVehicle("Vehicle 3", 25, 90, latitude, longitude);
            Vehicle v4 = createVehicle("Vehicle 4", 20, 100, latitude, longitude);
            em.persist(v1);
            em.persist(v2);
            em.persist(v3);
            em.persist(v4);
            transaction.commit();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
        } finally {
            try {
                if (transaction.getStatus() == Status.STATUS_ACTIVE) {
                    transaction.rollback();
                }
            } catch (Exception e) {
                LOG.log(Level.SEVERE, null, e);
            }
        }
    }

    private Vehicle createVehicle(String description, float capacity, float range, double latitude, double longitude) {
        Vehicle vehicle = new Vehicle();
        vehicle.setDescription(description);
        vehicle.setLocation(new Point(latitude, longitude));
        vehicle.setCapacity(BigDecimal.valueOf(capacity));
        vehicle.setRange(BigDecimal.valueOf(range));
        return vehicle;
    }

}
