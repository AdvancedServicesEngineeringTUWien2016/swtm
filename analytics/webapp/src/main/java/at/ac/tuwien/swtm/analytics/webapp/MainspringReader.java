package at.ac.tuwien.swtm.analytics.webapp;


import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataAdapter;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataListAdapter;
import at.ac.tuwien.swtm.analytics.mainspring.client.admin.DevicesResource;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataListAdapterExt;
import at.ac.tuwien.swtm.analytics.mainspring.client.retrieval.RestRetriever;
import at.ac.tuwien.swtm.analytics.webapp.config.AnalyticsConfiguration;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Stateless
public class MainspringReader {
    private static final Logger LOG = Logger.getLogger(MainspringReader.class.getName());

    @Inject
    private DevicesResource devicesResource;

    @Inject
    private RestRetriever retrievalResource;

    @Inject
    private AnalyticsConfiguration analyticsConfiguration;

    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    public void readMainspring() {
        DeviceDataListAdapterExt devices = devicesResource.get(analyticsConfiguration.getMainspringApiKey(), null, null, null, null);
        String deviceNames = devices.getDevices().stream().map(DeviceDataAdapter::getName).collect(Collectors.joining(":"));
        DeviceDataListAdapter deviceData = retrievalResource.getList(analyticsConfiguration.getMainspringApiKey(), deviceNames, null, null, null, "desc", null, null);

        LOG.info("Received " + devices.getDevices().size() + " devices");
    }
}
