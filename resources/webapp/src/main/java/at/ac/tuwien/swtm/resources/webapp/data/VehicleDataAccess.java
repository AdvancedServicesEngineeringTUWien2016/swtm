package at.ac.tuwien.swtm.resources.webapp.data;

import at.ac.tuwien.swtm.resources.model.Vehicle;
import com.blazebit.persistence.CriteriaBuilderFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class VehicleDataAccess {

    @Inject
    private EntityManager em;

    @Inject
    private CriteriaBuilderFactory cbf;

    public List<Vehicle> getAll() {
        return cbf.create(em, Vehicle.class)
                .getResultList();
    }
}
