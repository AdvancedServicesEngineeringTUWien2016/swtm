package at.ac.tuwien.swtm.notification.client;

import at.ac.tuwien.swtm.common.config.api.CommonConfiguration;
import at.ac.tuwien.swtm.notification.rest.api.NotificationsResource;
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
    public NotificationsResource produceNotificationsClient() {
        ResteasyWebTarget webTarget = (ResteasyWebTarget) ClientBuilder.newBuilder().build().target(getNotificationsUrl());
        return webTarget
                .register(LoggingClientRequestFilter.class)
                .proxy(NotificationsResource.class);
    }

    private String getNotificationsUrl() {
        return getEffectiveUrl("http://notification-service:8080", "notification-webapp/");
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
