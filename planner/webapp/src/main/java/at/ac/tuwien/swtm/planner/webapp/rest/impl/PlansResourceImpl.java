package at.ac.tuwien.swtm.planner.webapp.rest.impl;

import at.ac.tuwien.swtm.planner.rest.api.PlansResource;
import at.ac.tuwien.swtm.planner.rest.api.model.PlanRepresentation;
import at.ac.tuwien.swtm.planner.webapp.task.PlanningTask;
import at.ac.tuwien.swtm.planner.webapp.task.Vehicle;
import at.ac.tuwien.swtm.planner.webapp.task.Wastebin;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
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
        planningTask.run();

        Map<Long, List<Long>> result = planningTask.getSolution().getWastebins().stream()
                .filter(wastebin -> wastebin.getAssignedVehicle() != null)
                .collect(Collectors.groupingBy(wastebin -> wastebin.getAssignedVehicle().getId(), Collectors.mapping(Wastebin::getId, Collectors.toList())));

        return new PlanRepresentation(result);
    }
}
