package at.ac.tuwien.swtm.planner.webapp.config;

import at.ac.tuwien.swtm.common.config.api.Configuration;
import org.apache.tamaya.inject.ConfiguredProperty;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 01.06.2016.
 */
@Configuration
public interface PlannerConfiguration {

    public static final String GOOGLE_MAPS_API_KEY_ENVVAR = "GOOGLE_MAPS_API_KEY";
    public static final String GOOGLE_MAPS_API_KEY_PROPERTY = "swtm.googleMapsApiKey";

    @ConfiguredProperty(keys = {GOOGLE_MAPS_API_KEY_PROPERTY, GOOGLE_MAPS_API_KEY_ENVVAR})
    public String getGoogleMapsApiKey();

}
