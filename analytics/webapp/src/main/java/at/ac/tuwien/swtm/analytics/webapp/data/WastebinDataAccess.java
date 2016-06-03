package at.ac.tuwien.swtm.analytics.webapp.data;

import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import at.ac.tuwien.swtm.analytics.model.WastebinMomentState;
import com.blazebit.persistence.CriteriaBuilderFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 31.05.2016.
 */
@Stateless
public class WastebinDataAccess {

    @Inject
    private EntityManager em;

    @Inject
    private CriteriaBuilderFactory cbf;

    public List<WastebinMoment> getLatestWastebinMoments() {
        return cbf.create(em, WastebinMoment.class)
                .innerJoinFetch("wastebin", "bin")
                .whereNotExists()
                    .from(WastebinMomentState.class, "state")
                    .where("state.wastebinMoment.id").eqExpression("OUTER(id)")
                    .where("state.id.state").in(WastebinMomentState.State.getFailureStates())
                .end()
                .groupBy("bin.id")
                .having("timestamp").ge().all()
                    .from(WastebinMoment.class)
                    .select("timestamp")
                    .whereNotExists()
                        .from(WastebinMomentState.class, "state")
                        .where("state.wastebinMoment.id").eqExpression("OUTER(id)")
                        .where("state.id.state").in(WastebinMomentState.State.getFailureStates())
                    .end()
                    .where("wastebin.id").eqExpression("OUTER(bin.id)")
                .end()
                .getResultList();
    }

    public Optional<WastebinMoment> getLatestStateInactiveMoment(Long wastebinId, WastebinMomentState.State state) {
        List<WastebinMoment> wastebinMoments = cbf.create(em, WastebinMoment.class)
                .innerJoinFetch("wastebin", "bin")
                .where("bin.id").eq(wastebinId)
                .whereNotExists()
                    .from(WastebinMomentState.class, "state")
                    .where("state.wastebinMoment.id").eqExpression("OUTER(id)")
                    .where("state.id.state").eq(state)
                .end()
                .where("timestamp").ge().all()
                    .from(WastebinMoment.class)
                    .select("timestamp")
                    .whereNotExists()
                        .from(WastebinMomentState.class, "state")
                        .where("state.wastebinMoment.id").eqExpression("OUTER(id)")
                        .where("state.id.state").eq(state)
                    .end()
                    .where("wastebin.id").eqExpression("OUTER(bin.id)")
                .end()
                .getResultList();

        if (wastebinMoments.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(wastebinMoments.get(0));
        }
    }

    public Optional<Wastebin> getWastebin(Long id) {
        List<Wastebin> wastebins = cbf.create(em, Wastebin.class)
                .where("id").eq(id)
                .getResultList();

        if (wastebins.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(wastebins.get(0));
        }
    }
}
