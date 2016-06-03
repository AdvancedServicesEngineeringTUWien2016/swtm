package at.ac.tuwien.swtm.analytics.webapp.processor;

import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import org.apache.camel.Exchange;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 04.06.2016.
 */
public class GPSFailureMitigationProcessor {

    public void process(Exchange exchange) {
        WastebinMoment wastebinMoment = (WastebinMoment) exchange.getIn().getBody();
        Wastebin wastebin = wastebinMoment.getWastebin();

        if (wastebin.getFixedLocation() != null) {
            // fixed location overrules
            wastebinMoment.setOverriddenLocation(wastebinMoment.getLocation());
            wastebinMoment.setLocation(wastebin.getFixedLocation());
        }
    }
}
