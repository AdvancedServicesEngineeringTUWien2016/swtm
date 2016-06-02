package at.ac.tuwien.swtm.analytics.webapp.data;

import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import com.blazebit.persistence.CriteriaBuilderFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

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
                .groupBy("bin.id")
                .having("timestamp").ge().all()
                    .from(WastebinMoment.class)
                    .select("timestamp")
                    .where("wastebin.id").eqExpression("OUTER(bin.id)")
                .end()
                .getResultList();
    }
}
