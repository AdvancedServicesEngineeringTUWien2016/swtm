package at.ac.tuwien.swtm.analytics.webapp;

import at.ac.tuwien.swtm.analytics.event.GPSConflict;
import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.analytics.mainspring.client.ClientProducer;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataAdapter;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataListAdapterExt;
import at.ac.tuwien.swtm.analytics.mainspring.client.admin.DevicesResource;
import at.ac.tuwien.swtm.analytics.mainspring.client.retrieval.RestRetriever;
import at.ac.tuwien.swtm.analytics.webapp.config.AnalyticsConfiguration;
import at.ac.tuwien.swtm.analytics.webapp.processor.FillingDegreeValidator;
import at.ac.tuwien.swtm.analytics.webapp.processor.GPSFailureMitigationProcessor;
import at.ac.tuwien.swtm.analytics.webapp.processor.MainspringDataProcessor;
import at.ac.tuwien.swtm.analytics.webapp.processor.PayloadValidator;
import at.ac.tuwien.swtm.analytics.webapp.service.WastebinDataService;
import at.ac.tuwien.swtm.common.config.api.CommonConfiguration;
import io.fabric8.annotations.ServiceName;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.CdiEventEndpoint;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 28.05.2016.
 */
@ApplicationScoped
@Startup
@ContextName("analytics-context")
public class CamelRouteConfiguration extends RouteBuilder {

    @Inject
    @ServiceName("mainspring-service")
    private Instance<String> mainspringUrl;

    @Inject
    private CommonConfiguration commonConfig;

    @Inject
    private AnalyticsConfiguration analyticsConfiguration;

    @Inject
    private CdiEventEndpoint<GPSConflict> gpsConflictEventEndpoint;

    @Inject
    private CdiEventEndpoint<SensorFailure> sensorFailureEventEndpoint;

    @Override
    public void configure() throws Exception {
        getContext().setTracing(true);

        from("timer://sensorFetch?fixedRate=true&period=5s")
                .setExchangePattern(ExchangePattern.InOut)
                .setHeader(CxfConstants.OPERATION_NAME, constant("get"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/xml"))
                .setBody(constant(new Object[]{ analyticsConfiguration.getMainspringApiKey(), "", "", "15", new UriInfoImpl(null, null) }))
                .to("cxfrs://" + getMainspringUrl() + "?resourceClasses=" + DevicesResource.class.getName() + "&httpClientAPI=false"/*&loggingFeatureEnabled=true"*/)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        DeviceDataListAdapterExt devices = (DeviceDataListAdapterExt) exchange.getIn().getBody();
                        String deviceNames = devices.getDevices().stream().map(DeviceDataAdapter::getName).collect(Collectors.joining(":"));
                        Message in = exchange.getIn();
                        in.setHeader(CxfConstants.OPERATION_NAME, "getList");
                        in.setBody(new Object[]{ analyticsConfiguration.getMainspringApiKey(), deviceNames, new UriInfoImpl(null, null), "2014-01-01 01:00:00.000", "3000-01-01 01:00:00.000", "desc", "1", "false" });
                    }
                })
                .to("cxfrs://" + getMainspringUrl() + "?resourceClasses=" + RestRetriever.class.getName() + "&httpClientAPI=false"/*&loggingFeatureEnabled=true"*/)
                .bean(MainspringDataProcessor.class, "process")
                .split(body())
                .bean(GPSFailureMitigationProcessor.class, "process")
                .bean(PayloadValidator.class, "validate")
                .bean(FillingDegreeValidator.class, "validate")
                .bean(WastebinDataService.class, "persistWastebinMoment");

        from(gpsConflictEventEndpoint)
                .to("activemq:queue:GPSConflictEventQueue?brokerURL=tcp://localhost:61616");

        from(sensorFailureEventEndpoint)
                .to("activemq:queue:SensorFailureEventQueue?brokerURL=tcp://localhost:61616");
    }

    private String getMainspringUrl() {
        return getEffectiveUrl(mainspringUrl, "cs-ws/resources/");
    }

    private String getEffectiveUrl(Instance<String> serviceUrl, String contextRoot) {
        if ("local".equals(commonConfig.getStage())) {
            return "http://localhost:8080/" + contextRoot;
        } else {
            return serviceUrl.get() + "/" + contextRoot;
        }
    }

    public static class LoggingClientRequestFilter implements ClientRequestFilter {
        private static final Logger LOG = Logger.getLogger(ClientProducer.LoggingClientRequestFilter.class.getName());

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
            LOG.info(requestContext.getUri().toString());
        }

    }
}
