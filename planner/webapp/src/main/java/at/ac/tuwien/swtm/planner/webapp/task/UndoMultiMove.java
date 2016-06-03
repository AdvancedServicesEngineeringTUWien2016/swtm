package at.ac.tuwien.swtm.planner.webapp.task;

import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
public class UndoMultiMove extends AbstractMove {

    private Wastebin entity1;
    private Vehicle toVehicle1;
    private Wastebin entity2;
    private Vehicle toVehicle2;

    public UndoMultiMove(Wastebin entity1, Vehicle toVehicle1, Wastebin entity2, Vehicle toVehicle2) {
        this.entity1 = entity1;
        this.toVehicle1 = toVehicle1;
        this.entity2 = entity2;
        this.toVehicle2 = toVehicle2;
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(entity1, "previousRouteComponent"); // before changes are made to the queen.row
        entity1.setPreviousRouteComponent(toVehicle1);
        scoreDirector.afterVariableChanged(entity1, "previousRouteComponent"); // after changes are made to the queen.row

        scoreDirector.beforeVariableChanged(entity2, "previousRouteComponent"); // before changes are made to the queen.row
        entity2.setPreviousRouteComponent(toVehicle2);
        scoreDirector.afterVariableChanged(entity2, "previousRouteComponent"); // after changes are made to the queen.row

    }

    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return true;
    }

    @Override
    public Move createUndoMove(ScoreDirector scoreDirector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<? extends Object> getPlanningEntities() {
        return Arrays.asList(entity1, entity2);
    }

    @Override
    public Collection<? extends Object> getPlanningValues() {
        return Arrays.asList(toVehicle1, toVehicle2);
    }
}
