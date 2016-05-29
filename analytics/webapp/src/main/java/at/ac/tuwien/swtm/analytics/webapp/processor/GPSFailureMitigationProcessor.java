package at.ac.tuwien.swtm.analytics.webapp.processor;

import at.ac.tuwien.swtm.analytics.event.GPSConflict;
import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.analytics.event.SensorType;
import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import org.apache.camel.Exchange;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class GPSFailureMitigationProcessor {

    @Inject
    private Event<SensorFailure> sensorFailureEvent;

    @Inject
    private Event<GPSConflict> gpsConflictEvent;

    public void process(Exchange exchange) {
        WastebinMoment wastebinMoment = (WastebinMoment) exchange.getIn().getBody();
        Wastebin wastebin = wastebinMoment.getWastebin();
        if (wastebinMoment.getLocation() == null) {
            if (wastebin.getFixedLocation() == null) {
                sensorFailureEvent.fire(new SensorFailure(wastebin.getId(), wastebin.getName(), SensorType.GPS));
                exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
            } else {
                wastebinMoment.setLocation(wastebin.getFixedLocation());
            }
        } else {
            if (wastebin.getFixedLocation() != null && !wastebinMoment.getLocation().equals(wastebin.getFixedLocation())) {
                gpsConflictEvent.fire(new GPSConflict(wastebin.getId(), wastebin.getName()));
            }
        }
    }
}
