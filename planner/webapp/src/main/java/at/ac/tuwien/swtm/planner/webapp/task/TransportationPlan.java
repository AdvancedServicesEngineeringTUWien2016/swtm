package at.ac.tuwien.swtm.planner.webapp.task;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.Collection;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
@PlanningSolution
public class TransportationPlan implements Solution<BendableScore> {

    private final List<Wastebin> wastebins;
    private final List<Vehicle> vehicles;
    private BendableScore score;

    public TransportationPlan(List<Wastebin> wastebins, List<Vehicle> vehicles) {
        this.wastebins = wastebins;
        this.vehicles = vehicles;
    }

    @ValueRangeProvider(id = "vehicleRange")
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @PlanningEntityCollectionProperty
    public List<Wastebin> getWastebins() {
        return wastebins;
    }

    @Override
    public BendableScore getScore() {
        return score;
    }

    @Override
    public void setScore(BendableScore bendableScore) {
        this.score = bendableScore;
    }

    @Override
    public Collection<?> getProblemFacts() {
        return null;
    }
}
