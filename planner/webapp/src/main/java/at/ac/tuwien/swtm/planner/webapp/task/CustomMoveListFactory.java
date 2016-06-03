package at.ac.tuwien.swtm.planner.webapp.task;

import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveListFactory;
import org.optaplanner.core.impl.heuristic.selector.move.generic.chained.SubChainChangeMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
public class CustomMoveListFactory implements MoveListFactory<TransportationPlan> {
    @Override
    public List<? extends Move> createMoveList(TransportationPlan solution) {
        List<MultiMove> multiMoves = new ArrayList<>();
        List<Wastebin> wastebins = solution.getWastebins();
        for (Vehicle vehicle : solution.getVehicles()) {
            for (int i = 0; i < wastebins.size(); i++) {
                for (int j = 0; j < i; j++) {
                    multiMoves.add(new MultiMove(wastebins.get(i), wastebins.get(j), vehicle));
                    multiMoves.add(new MultiMove(wastebins.get(j), wastebins.get(i), vehicle));
                }
            }
        }
        return multiMoves;
    }
}
