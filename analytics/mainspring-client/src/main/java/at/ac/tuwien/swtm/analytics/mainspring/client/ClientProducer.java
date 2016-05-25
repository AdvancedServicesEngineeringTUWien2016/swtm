package at.ac.tuwien.swtm.analytics.mainspring.client;

import at.ac.tuwien.swtm.analytics.mainspring.client.admin.DevicesResource;
import at.ac.tuwien.swtm.analytics.mainspring.client.retrieval.RestRetriever;
import at.ac.tuwien.swtm.common.config.api.CommonConfiguration;
import io.fabric8.annotations.ServiceName;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
public class ClientProducer {

    @Inject
    @ServiceName("mainspring-service")
    private Instance<String> mainspringUrl;

    @Inject
    private CommonConfiguration commonConfig;

    @Produces
    public DevicesResource produceDevicesClient() {
        ResteasyWebTarget webTarget = (ResteasyWebTarget) ClientBuilder.newBuilder().build().target(getMainspringUrl());
        return webTarget
                .register(LoggingClientRequestFilter.class)
                .proxy(DevicesResource.class);
    }

    @Produces
    public RestRetriever produceRetrievalClient() {
        ResteasyWebTarget webTarget = (ResteasyWebTarget) ClientBuilder.newBuilder().build().target(getMainspringUrl());
        return webTarget
                .register(LoggingClientRequestFilter.class)
                .proxy(RestRetriever.class);
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
        private static final Logger LOG = Logger.getLogger(LoggingClientRequestFilter.class.getName());

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
            LOG.info(requestContext.getUri().toString());
        }

    }
}
