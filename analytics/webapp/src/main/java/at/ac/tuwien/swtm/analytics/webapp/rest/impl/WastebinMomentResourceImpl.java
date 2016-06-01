package at.ac.tuwien.swtm.analytics.webapp.rest.impl;

import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import at.ac.tuwien.swtm.analytics.rest.api.WastebinMomentResource;
import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinMomentRepresentation;
import at.ac.tuwien.swtm.analytics.webapp.data.WastebinDataAccess;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 31.05.2016.
 */
public class WastebinMomentResourceImpl implements WastebinMomentResource {

    @Inject
    private WastebinDataAccess wastebinDataAccess;

    @Override
    public List<WastebinMomentRepresentation> getLatestMoments() {
        List<WastebinMoment> moments = wastebinDataAccess.getLatestWastebinMoments();
        return moments.stream().map(moment -> {
            WastebinMomentRepresentation representation = new WastebinMomentRepresentation();
            representation.setId(moment.getWastebin().getId());
            representation.setName(moment.getWastebin().getName());
            representation.setFillingDegree(moment.getFillingDegree());
            representation.setPayload(moment.getPayload());
            representation.setTimestamp(moment.getTimestamp());
            representation.setLatitude(moment.getLocation().getLatitude());
            representation.setLongitude(moment.getLocation().getLongitude());
            return representation;
        }).collect(Collectors.toList());
    }

}
