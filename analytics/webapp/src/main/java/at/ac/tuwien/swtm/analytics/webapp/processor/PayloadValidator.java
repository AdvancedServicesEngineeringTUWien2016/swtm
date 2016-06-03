package at.ac.tuwien.swtm.analytics.webapp.processor;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class PayloadValidator {

    private static final BigDecimal payloadThreshold = new BigDecimal(300);

    @Inject
    private Event<SensorFailure> sensorFailureEvent;

    @Inject
    private WastebinDataAccess wastebinDataAccess;

    @Inject
    private WastebinDataService wastebinDataService;

    public void validate(Exchange exchange) {
        WastebinMoment wastebinMoment = (WastebinMoment) exchange.getIn().getBody();
        if (wastebinMoment.getPayload() == null
                || wastebinMoment.getPayload().compareTo(BigDecimal.ZERO) < 0
                || wastebinMoment.getPayload().compareTo(payloadThreshold) > 0) {
            Wastebin wastebin = wastebinMoment.getWastebin();
            wastebinDataService.saveWastebinMomentState(wastebinMoment.getId(), WastebinMomentState.State.PAYLOAD_FAILURE);
            LocalDateTime latestValidMomentTimestamp = wastebinDataAccess.getLatestStateInactiveMoment(wastebin.getId(), WastebinMomentState.State.PAYLOAD_FAILURE).map(WastebinMoment::getTimestamp).orElse(null);
            sensorFailureEvent.fire(new SensorFailure(SensorType.PAYLOAD, wastebin.getId(), wastebin.getName(), wastebinMoment.getTimestamp(), latestValidMomentTimestamp));
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
        }
    }

}
