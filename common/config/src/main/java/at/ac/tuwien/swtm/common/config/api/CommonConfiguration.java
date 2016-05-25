package at.ac.tuwien.swtm.common.config.api;

import org.apache.tamaya.inject.ConfiguredProperty;
import org.apache.tamaya.inject.DefaultValue;

@Configuration
public interface CommonConfiguration {

	public static final String STAGE_ENVVAR = "SWTM_STAGE";
	public static final String STAGE_PROPERTY = "swtm.stage";

	@DefaultValue("local")
	@ConfiguredProperty(keys = {STAGE_PROPERTY, STAGE_ENVVAR})
	public String getStage();
	
}
