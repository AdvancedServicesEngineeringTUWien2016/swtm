package at.ac.tuwien.swtm.planner.webapp.task;

import at.ac.tuwien.swtm.analytics.rest.api.WastebinMomentResource;
import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinMomentRepresentation;
import at.ac.tuwien.swtm.resources.rest.api.VehiclesResource;
import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
public class PlanningTask implements Runnable {

    @Inject
    private VehiclesResource vehiclesResource;

    @Inject
    private WastebinMomentResource wastebinMomentResource;

    @Override
    public void run() {
        SolverFactory<TransportationPlan> solverFactory = SolverFactory.createFromXmlResource(
                "META-INF/transportationSolverConfig.xml");
        Solver<TransportationPlan> solver = solverFactory.buildSolver();

        TransportationPlan solution = solver.solve(getProblem());
        // we need to assign wastebins to vehicles
    }

    private TransportationPlan getProblem() {
        List<Vehicle> vehicles = vehiclesResource.getVehicles().stream()
                .map(Vehicle::fromVehicleRepresentation)
                .collect(Collectors.toList());

        List<Wastebin> wastebins = wastebinMomentResource.getLatestMoments().stream()
                .map(Wastebin::fromWastebinRepresentation)
                .collect(Collectors.toList());

        return new TransportationPlan(wastebins, vehicles);
    }
}
