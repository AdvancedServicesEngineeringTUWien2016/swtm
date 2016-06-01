package at.ac.tuwien.swtm.planner.rest.api.model;

import java.util.List;
import java.util.Map;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 31.05.2016.
 */
public class PlanRepresentation {

    private Map<Long, List<Long>> vehicleAssignment;

    public PlanRepresentation() {}

    public PlanRepresentation(Map<Long, List<Long>> vehicleAssignment) {
        this.vehicleAssignment = vehicleAssignment;
    }

    public Map<Long, List<Long>> getVehicleAssignment() {
        return vehicleAssignment;
    }

    public void setVehicleAssignment(Map<Long, List<Long>> vehicleAssignment) {
        this.vehicleAssignment = vehicleAssignment;
    }
}
