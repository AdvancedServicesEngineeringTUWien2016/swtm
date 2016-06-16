package at.ac.tuwien.swtm.analytics.webapp;

import at.ac.tuwien.swtm.analytics.event.GPSConflict;
import at.ac.tuwien.swtm.analytics.event.SensorFailure;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataAdapter;
import at.ac.tuwien.swtm.analytics.mainspring.client.adapter.DeviceDataListAdapterExt;
import at.ac.tuwien.swtm.analytics.mainspring.client.admin.DevicesResource;
import at.ac.tuwien.swtm.analytics.mainspring.client.retrieval.RestRetriever;
import at.ac.tuwien.swtm.analytics.webapp.config.AnalyticsConfiguration;
import at.ac.tuwien.swtm.analytics.webapp.processor.*;
import at.ac.tuwien.swtm.analytics.webapp.service.WastebinDataService;
import at.ac.tuwien.swtm.common.config.api.CommonConfiguration;
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
import javax.inject.Inject;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;

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
    private CommonConfiguration commonConfig;

    @Inject
    private AnalyticsConfiguration analyticsConfiguration;

    @Inject
    private CdiEventEndpoint<GPSConflict> gpsConflictEventEndpoint;

    @Inject
    private CdiEventEndpoint<SensorFailure> sensorFailureEventEndpoint;

    @Override
    public void configure() throws Exception {
        final boolean enableTracing = false;
        getContext().setTracing(enableTracing);

        getContext().addComponent("activemq",  activeMQComponent("tcp://" + getActiveMqUrl()));

        from("timer://sensorFetch?fixedRate=true&period=5s")
                .setExchangePattern(ExchangePattern.InOut)
                .setHeader(CxfConstants.OPERATION_NAME, constant("get"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/xml"))
                .setBody(constant(new Object[]{ analyticsConfiguration.getMainspringApiKey(), "", "", "15", new UriInfoImpl(null, null) }))
                .to("cxfrs://" + getMainspringUrl() + "?resourceClasses=" + DevicesResource.class.getName() + "&httpClientAPI=false&loggingFeatureEnabled=" + enableTracing)
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
                .to("cxfrs://" + getMainspringUrl() + "?resourceClasses=" + RestRetriever.class.getName() + "&httpClientAPI=false&loggingFeatureEnabled=" + enableTracing)
                .bean(MainspringDataProcessor.class, "process")
                .split(body())
                .bean(GPSFailureMitigationProcessor.class, "process")
                .bean(WastebinDataService.class, "persistWastebinMoment")
                .choice().when(simple("${body} != null"))
                    .bean(GPSValidator.class, "process")
                    .bean(PayloadValidator.class, "validate")
                    .bean(FillingDegreeValidator.class, "validate")
                .endChoice();

        from(gpsConflictEventEndpoint)
                .to("activemq:queue:GPSConflictEventQueue?username=admin&password=admin");

        from(sensorFailureEventEndpoint)
                .to("activemq:queue:SensorFailureEventQueue?username=admin&password=admin");
    }

    private String getMainspringUrl() {
        return getEffectiveUrl("http://mainspring-service:8081", "cs-ws/resources/");
    }

    private String getActiveMqUrl() {
        if ("local".equals(commonConfig.getStage())) {
            return "localhost:61616";
        } else {
            return "activemq-service:61616";
        }
    }

    private String getEffectiveUrl(String serviceUrl, String contextRoot) {
        if ("local".equals(commonConfig.getStage())) {
            return "http://localhost:8080/" + contextRoot;
        } else {
            return serviceUrl + "/" + contextRoot;
        }
    }

}
