package at.ac.tuwien.swtm.resources.webapp.service;

import at.ac.tuwien.swtm.resources.model.Vehicle;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
@Stateless
public class VehicleService {

    @Inject
    private EntityManager em;

    public void saveVehicle(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            em.persist(vehicle);
        } else {
            em.merge(vehicle);
        }
    }

}
