package at.ac.tuwien.swtm.planner.webapp.task;

import org.optaplanner.core.impl.domain.entity.descriptor.EntityDescriptor;
import org.optaplanner.core.impl.domain.variable.descriptor.VariableDescriptor;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.heuristic.move.CompositeMove;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.generic.ChangeMove;
import org.optaplanner.core.impl.heuristic.selector.move.generic.chained.ChainedChangeMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.*;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
public class MultiMove extends AbstractMove {

    private Wastebin chainStart1;
    private Wastebin chainStart2;

    private Vehicle toPlanningVariable;

    public MultiMove(Wastebin chainStart1, Wastebin chainStart2, Vehicle toPlanningVariable) {
        this.chainStart1 = chainStart1;
        this.chainStart2 = chainStart2;
        this.toPlanningVariable = toPlanningVariable;
    }


    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return chainStart1.getVehicle() == null || !chainStart1.getVehicle().equals(chainStart2.getVehicle());
//        Set<Wastebin> chain1Entities = new HashSet<>();
//        Set<Wastebin> chain2Entities = new HashSet<>();
//        Wastebin current = chainStart1;
//        while (current != null) {
//            chain1Entities.add(current);
//            current = current.getNextWastebin();
//        }
//        current = chainStart2;
//        while (current != null) {
//            chain2Entities.add(current);
//            current = current.getNextWastebin();
//        }
//        return Collections.disjoint(chain1Entities, chain2Entities);
    }

    @Override
    public Move createUndoMove(ScoreDirector scoreDirector) {
        return new UndoMultiMove(chainStart1, chainStart1.getVehicle(), chainStart2, chainStart2.getVehicle());
    }

    @Override
    public Collection<? extends Object> getPlanningEntities() {
        return Arrays.asList(chainStart1, chainStart2);
    }

    @Override
    public Collection<? extends Object> getPlanningValues() {
        return Arrays.asList(toPlanningVariable);
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(chainStart1, "previousRouteComponent"); // before changes are made to the queen.row
        chainStart1.setPreviousRouteComponent(toPlanningVariable);
        scoreDirector.afterVariableChanged(chainStart1, "previousRouteComponent"); // after changes are made to the queen.row
        chainStart1.getPreviousRouteComponent().setNextWastebin(null);

        Wastebin chainEnd1 = chainStart1;
        while (chainEnd1.getNextWastebin() != null) {
            chainEnd1 = chainEnd1.getNextWastebin();
        }
        scoreDirector.beforeVariableChanged(chainStart2, "previousRouteComponent"); // before changes are made to the queen.row
        chainStart2.setPreviousRouteComponent(chainEnd1);
        scoreDirector.afterVariableChanged(chainStart2, "previousRouteComponent"); // after changes are made to the queen.row
        chainStart2.getPreviousRouteComponent().setNextWastebin(null);
    }
}
