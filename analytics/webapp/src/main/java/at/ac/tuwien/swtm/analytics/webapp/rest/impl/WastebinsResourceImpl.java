package at.ac.tuwien.swtm.analytics.webapp.rest.impl;

import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.rest.api.WastebinsResource;
import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinRepresentation;
import at.ac.tuwien.swtm.analytics.webapp.data.WastebinDataAccess;
import at.ac.tuwien.swtm.analytics.webapp.service.WastebinDataService;
import at.ac.tuwien.swtm.common.model.Point;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
public class WastebinsResourceImpl implements WastebinsResource {

    @Inject
    private WastebinDataAccess wastebinDataAccess;

    @Inject
    private WastebinDataService wastebinDataService;

    @Override
    public WastebinRepresentation getWastebin(Long wastebinId) {
        return wastebinDataAccess.getWastebin(wastebinId)
                .map(wastebin -> {
                    WastebinRepresentation wastebinRepresentation = new WastebinRepresentation();
                    wastebinRepresentation.setId(wastebin.getId());
                    wastebinRepresentation.setName(wastebin.getName());
                    wastebinRepresentation.setFixedLatitude(wastebin.getFixedLocation() == null ? null : wastebin.getFixedLocation().getLatitude());
                    wastebinRepresentation.setFixedLongitude(wastebin.getFixedLocation() == null ? null : wastebin.getFixedLocation().getLongitude());
                    return wastebinRepresentation;
                }).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @Override
    public Response updateWastebin(WastebinRepresentation wastebinRepresentation) {
        Wastebin wastebin = new Wastebin();
        wastebin.setId(wastebinRepresentation.getId());
        wastebin.setName(wastebinRepresentation.getName());
        wastebin.setFixedLocation(new Point(wastebinRepresentation.getFixedLatitude(), wastebinRepresentation.getFixedLongitude()));

        wastebinDataService.updateWastebin(wastebin);

        return Response.ok().build();
    }
}
