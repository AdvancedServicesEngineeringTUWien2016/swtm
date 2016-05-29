package at.ac.tuwien.swtm.analytics.webapp.service;

import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import com.blazebit.persistence.CriteriaBuilderFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
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

    public void persistWastebinMoment(WastebinMoment wastebinMoment) {
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
            if (wastebinMoment.getFillingDegree() == null || wastebinMoment.getFillingDegree().compareTo(BigDecimal.ZERO) < 0 || wastebinMoment.getFillingDegree().compareTo(BigDecimal.ONE) > 0) {
                throw new IllegalArgumentException("Filling degree invalid: " + wastebinMoment.getFillingDegree());
            }
            if (wastebinMoment.getLocation().getLatitude() == null || wastebinMoment.getLocation().getLongitude() == null) {
                throw new NullPointerException("A location component is null");
            }
            if (wastebinMoment.getPayload() == null || wastebinMoment.getPayload().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Payload invalid: " + wastebinMoment.getPayload());
            }
            em.persist(wastebinMoment);
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
}
