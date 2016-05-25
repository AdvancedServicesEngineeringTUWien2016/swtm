package at.ac.tuwien.swtm.common.config.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.DeploymentException;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.inject.Singleton;

import at.ac.tuwien.swtm.common.config.api.ClearConfigCacheEvent;
import at.ac.tuwien.swtm.common.config.api.Configuration;
import org.apache.tamaya.ConfigurationProvider;
import org.apache.tamaya.inject.ConfiguredItemSupplier;
import org.apache.tamaya.inject.internal.CachingConfigTemplateInvocationHandler;


public final class ConfigurationExtension implements Extension {
	
	private final Set<Bean<?>> beans = new HashSet<>();
	private final ConcurrentMap<Class<?>, CachingConfigTemplateInvocationHandler> invocationHandlers = new ConcurrentHashMap<>();

    public <T> void initializeConfiguredFields(final @Observes @WithAnnotations(Configuration.class) ProcessAnnotatedType<T> pit) {
        final AnnotatedType<T> at = pit.getAnnotatedType();
        if (!at.getJavaClass().isInterface()) {
        	throw new DeploymentException("Invalid configuration class '" + at.getJavaClass().getName() + "'! Only interfaces supported!");
        }

        Class<?> beanClass = at.getJavaClass();
        Class<?>[] types = new Class[] { at.getJavaClass(), Object.class };
        Annotation[] qualifiers = new Annotation[] { new DefaultLiteral()};
        Class<? extends Annotation> scope = Singleton.class;
        T instance = createTemplate(at.getJavaClass());
        Bean<T> bean = new CustomBean<T>(beanClass, types, qualifiers, scope, instance);

        beans.add(bean);
    }

    private <T> T createTemplate(Class<T> templateType) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if(cl==null){
            cl = this.getClass().getClassLoader();
        }
        CachingConfigTemplateInvocationHandler invocationHandler = new CachingConfigTemplateInvocationHandler(templateType, ConfigurationProvider.getConfiguration());
        invocationHandlers.put(templateType, invocationHandler);
        return (T) Proxy.newProxyInstance(cl, new Class[]{ConfiguredItemSupplier.class, Objects.requireNonNull(templateType)}, invocationHandler);
    }


    public <T> void initializeConfiguredFields(final @Observes AfterBeanDiscovery abd) {
    	for (Bean<?> bean : beans) {
    		abd.addBean(bean);
    	}
    }

    public void clearCache(@Observes ClearConfigCacheEvent clearConfigurationCacheEvent) {
    	CachingConfigTemplateInvocationHandler invocationHandler = invocationHandlers.get(clearConfigurationCacheEvent.getConfigurationClass());
    	invocationHandler.clearCache();
    }

}