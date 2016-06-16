package at.ac.tuwien.swtm.planner.client;

import at.ac.tuwien.swtm.common.config.api.CommonConfiguration;
import at.ac.tuwien.swtm.planner.rest.api.PlansResource;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

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
    private CommonConfiguration commonConfig;

    @Produces
    public PlansResource producePlannerClient() {
        ResteasyWebTarget webTarget = (ResteasyWebTarget) ClientBuilder.newBuilder().build().target(getPlannerUrl());
        return webTarget
                .register(LoggingClientRequestFilter.class)
                .proxy(PlansResource.class);
    }

    private String getPlannerUrl() {
        return getEffectiveUrl("http://planner-service:8080", "planner-webapp/");
    }

    private String getEffectiveUrl(String serviceUrl, String contextRoot) {
        if ("local".equals(commonConfig.getStage())) {
            return "http://localhost:8080/" + contextRoot;
        } else {
            return serviceUrl + "/" + contextRoot;
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
