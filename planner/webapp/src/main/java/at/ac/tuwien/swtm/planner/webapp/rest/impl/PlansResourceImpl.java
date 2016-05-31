package at.ac.tuwien.swtm.planner.webapp.rest.impl;

import at.ac.tuwien.swtm.planner.rest.api.PlansResource;
import at.ac.tuwien.swtm.planner.webapp.task.PlanningTask;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

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
    public Response createNewPlan() {
        managedExecutorService.submit(planningTaskFactory.get());
        return Response.ok().build();
    }
}
