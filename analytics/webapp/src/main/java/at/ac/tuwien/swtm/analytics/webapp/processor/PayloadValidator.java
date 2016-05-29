package at.ac.tuwien.swtm.analytics.webapp.processor;

import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.analytics.event.SensorType;
import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import org.apache.camel.Exchange;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class PayloadValidator {

    private static final BigDecimal payloadThreshold = new BigDecimal(300);

    @Inject
    private Event<SensorFailure> sensorFailureEvent;

    public void validate(Exchange exchange) {
        WastebinMoment wastebinMoment = (WastebinMoment) exchange.getIn().getBody();
        if (wastebinMoment.getPayload().compareTo(payloadThreshold) > 0) {
            Wastebin wastebin = wastebinMoment.getWastebin();
            sensorFailureEvent.fire(new SensorFailure(wastebin.getId(), wastebin.getName(), SensorType.PAYLOAD));
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
        }
    }

}
