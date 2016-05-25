package at.ac.tuwien.swtm.analytics.webapp.config;

import at.ac.tuwien.swtm.common.config.api.Configuration;
import org.apache.tamaya.inject.ConfiguredProperty;
import org.apache.tamaya.inject.DefaultValue;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Configuration
public interface AnalyticsConfiguration {

    public static final String MAINSPRING_API_KEY_ENVVAR = "MAINSPRING_API_KEY";
    public static final String MAINSPRING_API_KEY_PROPERTY = "mainspring.apikey";

    @DefaultValue("admin")
    @ConfiguredProperty(keys = {MAINSPRING_API_KEY_PROPERTY, MAINSPRING_API_KEY_ENVVAR})
    public String getMainspringApiKey();

}
