package at.ac.tuwien.swtm.common.config.api;


public class ClearConfigCacheEvent {

	private final Class<?> configurationClass;

	public ClearConfigCacheEvent(Class<?> configurationClass) {
		this.configurationClass = configurationClass;
	}

	public Class<?> getConfigurationClass() {
		return configurationClass;
	}
}
