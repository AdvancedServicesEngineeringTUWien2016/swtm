package at.ac.tuwien.swtm.planner.webapp.task;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 02.06.2016.
 */
@PlanningEntity
public interface RouteComponent {

    public Vehicle getVehicle();

    @InverseRelationShadowVariable(sourceVariableName = "previousRouteComponent")
    public Wastebin getNextWastebin();
    public void setNextWastebin(Wastebin nextWastebin);

}
