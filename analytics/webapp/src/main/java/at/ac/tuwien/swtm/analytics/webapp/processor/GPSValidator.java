package at.ac.tuwien.swtm.analytics.webapp.processor;

import at.ac.tuwien.swtm.analytics.event.GPSConflict;
import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.analytics.event.SensorType;
import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import at.ac.tuwien.swtm.analytics.model.WastebinMomentState;
import at.ac.tuwien.swtm.analytics.webapp.data.WastebinDataAccess;
import at.ac.tuwien.swtm.analytics.webapp.service.WastebinDataService;
import org.apache.camel.Exchange;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class GPSValidator {

    @Inject
    private Event<SensorFailure> sensorFailureEvent;

    @Inject
    private Event<GPSConflict> gpsConflictEvent;

    @Inject
    private WastebinDataAccess wastebinDataAccess;

    @Inject
    private WastebinDataService wastebinDataService;

    public void process(Exchange exchange) {
        WastebinMoment wastebinMoment = (WastebinMoment) exchange.getIn().getBody();
        Wastebin wastebin = wastebinMoment.getWastebin();
        if (wastebinMoment.getLocation() == null
                || wastebinMoment.getLocation().getLatitude() < 0
                || wastebinMoment.getLocation().getLongitude() < 0) {
            if (wastebin.getFixedLocation() == null) {
                wastebinDataService.saveWastebinMomentState(wastebinMoment.getId(), WastebinMomentState.State.GPS_FAILURE);
                LocalDateTime latestValidMomentTimestamp = wastebinDataAccess.getLatestStateInactiveMoment(wastebin.getId(), WastebinMomentState.State.GPS_FAILURE).map(WastebinMoment::getTimestamp).orElse(null);
                sensorFailureEvent.fire(new SensorFailure(SensorType.GPS, wastebin.getId(), wastebin.getName(), wastebinMoment.getTimestamp(), latestValidMomentTimestamp));
                exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
            }
        } else {
            if (wastebin.getFixedLocation() != null && !wastebinMoment.getLocation().equals(wastebinMoment.getOverriddenLocation())) {
                wastebinDataService.saveWastebinMomentState(wastebinMoment.getId(), WastebinMomentState.State.GPS_CONFLICT);
                LocalDateTime latestValidMomentTimestamp = wastebinDataAccess.getLatestStateInactiveMoment(wastebin.getId(), WastebinMomentState.State.GPS_CONFLICT).map(WastebinMoment::getTimestamp).orElse(null);
                gpsConflictEvent.fire(new GPSConflict(wastebin.getId(), wastebin.getName(), wastebinMoment.getTimestamp(), latestValidMomentTimestamp));
            }
        }
    }
}
