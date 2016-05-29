package at.ac.tuwien.swtm.analytics.webapp.processor;

import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import at.ac.tuwien.swtm.analytics.webapp.event.GPSConflict;
import at.ac.tuwien.swtm.analytics.webapp.event.GPSFailure;
import at.ac.tuwien.swtm.analytics.webapp.service.WastebinDataService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class GPSFailureMitigationProcessor {

    @Inject
    private Event<GPSFailure> gpsFailureEvent;

    @Inject
    private Event<GPSConflict> gpsConflictEvent;

    public void process(WastebinMoment wastebinMoment) {
        Wastebin wastebin = wastebinMoment.getWastebin();
        if (wastebinMoment.getLocation() == null) {
            if (wastebin.getFixedLocation() == null) {
                gpsFailureEvent.fire(new GPSFailure(wastebin.getName()));
            } else {
                wastebinMoment.setLocation(wastebin.getFixedLocation());
            }
        } else {
            if (wastebin.getFixedLocation() != null && !wastebinMoment.getLocation().equals(wastebin.getFixedLocation())) {
                gpsConflictEvent.fire(new GPSConflict(wastebin.getName()));
            }
        }
    }
}
