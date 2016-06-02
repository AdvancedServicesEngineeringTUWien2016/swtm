package at.ac.tuwien.swtm.analytics.webapp.processor;

import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.*;
import at.ac.tuwien.swtm.analytics.model.Wastebin;
import at.ac.tuwien.swtm.analytics.model.WastebinMoment;
import at.ac.tuwien.swtm.analytics.webapp.constant.SensorConstants;
import at.ac.tuwien.swtm.analytics.webapp.service.WastebinDataService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class MainspringDataProcessor {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Inject
    private WastebinDataService wastebinDataService;

    public void process(Exchange exchange) {
        DeviceDataListAdapter deviceData = (DeviceDataListAdapter) exchange.getIn().getBody();

        List<WastebinMoment> wastebinMoments = new ArrayList<>();
        for (DeviceDataAdapter deviceDataAdapter : deviceData.getDevices()) {
            String deviceName = deviceDataAdapter.getName();
            Wastebin wastebin = wastebinDataService.getOrCreateWastebin(deviceName);
            wastebin.setName(deviceName);

            WastebinMoment wastebinMoment = new WastebinMoment();
            wastebinMoment.setWastebin(wastebin);
            wastebinMoments.add(wastebinMoment);

            Optional<SensorDataAdapter> sensorDataAdapter = deviceDataAdapter.getSensors().stream().filter(adapter -> SensorConstants.WASTEBIN_SENSOR_NAME.equals(adapter.getName())).findAny();
            if (sensorDataAdapter.isPresent() && !sensorDataAdapter.get().getAttributes().isEmpty()) {
                for (AttributeDataAdapter attributeDataAdapter : sensorDataAdapter.get().getAttributes()) {
                    if (!attributeDataAdapter.getMoments().isEmpty()) {
                        MomentAdapter moment = attributeDataAdapter.getMoments().get(0);
                        wastebinMoment.setTimestamp(LocalDateTime.parse(moment.getTimestamp(), formatter));
                        if (SensorConstants.FILLING_DEGREE_NAME.equals(attributeDataAdapter.getName())) {
                            wastebinMoment.setFillingDegree(new BigDecimal(moment.getValue()));
                        } else if (SensorConstants.PAYLOAD_NAME.equals(attributeDataAdapter.getName())) {
                            wastebinMoment.setPayload(new BigDecimal(moment.getValue()));
                        } else if (SensorConstants.LOCATION_NAME.equals(attributeDataAdapter.getName())) {
                            String[] parts = moment.getValue().split("\\|");
                            // we have to convert it like this because Mainspring is stupid
                            Double latitude = Double.valueOf(parts[1]) / 10000.0;
                            Double longitude = Double.valueOf(parts[0])  / 10000.0;

                            if (latitude < 0 || longitude < 0) {
                                wastebinMoment.setLocation(null);
                            } else {
                                wastebinMoment.getLocation().setLatitude(latitude);
                                wastebinMoment.getLocation().setLongitude(longitude);
                            }

                        }
                    }
                }
            }
        }
        exchange.getIn().setBody(wastebinMoments);
    }
}
