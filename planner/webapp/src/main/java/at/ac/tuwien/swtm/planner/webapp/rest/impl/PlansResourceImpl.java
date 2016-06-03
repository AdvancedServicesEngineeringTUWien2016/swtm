package at.ac.tuwien.swtm.planner.webapp.rest.impl;

import at.ac.tuwien.swtm.planner.rest.api.PlansResource;
import at.ac.tuwien.swtm.planner.rest.api.model.PlanRepresentation;
import at.ac.tuwien.swtm.planner.webapp.task.PlanningTask;
import at.ac.tuwien.swtm.planner.webapp.task.TransportationPlanScoreCalculator;
import at.ac.tuwien.swtm.planner.webapp.task.Wastebin;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
public class PlansResourceImpl implements PlansResource {

    @Resource
    private ManagedExecutorService managedExecutorService;

    @Inject
    private Instance<PlanningTask> planningTaskFactory;

    @Override
    public PlanRepresentation createNewPlan() {
//        managedExecutorService.submit(planningTaskFactory.get());
        PlanningTask planningTask = planningTaskFactory.get();
        try {
            planningTask.run();
        } catch(Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        if (planningTask.getSolution().getScore().isFeasible()) {
            Map<Long, List<Long>> result = planningTask.getSolution().getVehicles().stream()
                    .filter(vehicle -> vehicle.getNextWastebin() != null)
                    .collect(
                            Collectors.toMap(
                                vehicle -> vehicle.getId(),
                                vehicle -> TransportationPlanScoreCalculator.getRoute(vehicle).stream().map(Wastebin::getId).collect(Collectors.toList())
                            )
                    );

            return new PlanRepresentation(result);
        } else {
            return null;
        }
    }
}
