package at.ac.tuwien.swtm.analytics.webapp.service;

import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import at.ac.tuwien.swtm.analytics.model.WastebinMomentState;
import at.ac.tuwien.swtm.analytics.model.WastebinStateId;
import com.blazebit.persistence.CriteriaBuilderFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
@Stateless
public class WastebinDataService {

    @Inject
    private EntityManager em;

    @Inject
    private CriteriaBuilderFactory cbf;

    public WastebinMoment persistWastebinMoment(WastebinMoment wastebinMoment) {
        Long existingMoments = cbf.create(em, Long.class)
                .from(WastebinMoment.class, "moment")
                .select("COUNT(*)")
                .where("moment.wastebin.id").eq(wastebinMoment.getWastebin().getId())
                .where("moment.timestamp").ge(wastebinMoment.getTimestamp())
                .getSingleResult();

        // only save if this  moment is new
        if (existingMoments == 0) {
            if (wastebinMoment.getWastebin().getId() == null) {
                wastebinMoment.setWastebin(getOrCreateWastebin(wastebinMoment.getWastebin().getName()));
            }
            em.persist(wastebinMoment);
            return wastebinMoment;
        } else {
            return null;
        }
    }

    public Wastebin getOrCreateWastebin(String name) {
        List<Wastebin> wastebins = cbf.create(em, Wastebin.class)
                .where("name").eq(name)
                .getResultList();

        Wastebin wastebin;
        if (wastebins.isEmpty()) {
            wastebin = new Wastebin();
            wastebin.setName(name);
            em.persist(wastebin);
        } else {
            wastebin = wastebins.get(0);
        }
        return wastebin;
    }

    public void updateWastebin(Wastebin wastebin) {
        em.merge(wastebin);
    }

    public void saveWastebinMomentState(Long wastebinMomentId, WastebinMomentState.State state) {
        em.persist(new WastebinMomentState(new WastebinStateId(wastebinMomentId, state)));
    }
}
