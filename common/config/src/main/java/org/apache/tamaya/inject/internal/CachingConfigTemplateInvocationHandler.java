package org.apache.tamaya.inject.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.tamaya.Configuration;
import org.apache.tamaya.ConfigurationProvider;
import org.apache.tamaya.TypeLiteral;
import org.apache.tamaya.inject.DynamicValue;
import org.apache.tamaya.inject.NoConfig;
import org.apache.tamaya.inject.internal.ConfiguredType;
import org.apache.tamaya.inject.internal.DefaultDynamicValue;
import org.apache.tamaya.inject.internal.InjectionUtils;

/**
 * Invocation handler that handles request against a configuration template.
 */
public final class CachingConfigTemplateInvocationHandler implements InvocationHandler, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Object NULL_OBJECT = new Object();
	
    /**
     * The configured type.
     */
    private transient ConfiguredType type;

    /**
     * The configuration instance of this proxy.
     */
    private transient Configuration configuration;
    
    private Class<?> typeClass;
    
    private final ConcurrentMap<String, Object> resultCache = new ConcurrentHashMap<String, Object>();

    /**
     * Creates a new handler instance.
     *
     * @param type          the target type, not null.
     * @param configuration the configuration to be used for evaluating the values for injection into {@code instance},
     *                      not null.
     */
    public CachingConfigTemplateInvocationHandler(Class<?> type, Configuration configuration) {
        Objects.requireNonNull(configuration);
        this.typeClass = type;
        this.type = new ConfiguredType(Objects.requireNonNull(type));
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Can only proxy interfaces as configuration templates.");
        }
        this.configuration = Objects.requireNonNull(configuration);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("toString".equals(method.getName())) {
            return "Configured Proxy -> " + this.type.getType().getName();
        } else if ("hashCode".equals(method.getName())) {
            return Objects.hashCode(proxy);
        } else if ("equals".equals(method.getName())) {
            return Objects.equals(proxy, args[0]);
        } else if ("get".equals(method.getName())) {
            return this.configuration;
        }
        if (method.getReturnType() == DynamicValue.class) {
            return DefaultDynamicValue.of(method, configuration);
        }
        
        Object result = resultCache.get(method.getName());
        
        if (result != null) {
            if (result == NULL_OBJECT) {
            	return null;
            }
            
        	return result;
        }
        
        if (!method.isAnnotationPresent(NoConfig.class)) {
	        String configValue = InjectionUtils.getConfigValue(method);
	        result = InjectionUtils.adaptValue(method, TypeLiteral.of(method.getReturnType()), configValue);
        }
        
        if (result == null && method.isDefault()) {
        	final Field field = Lookup.class.getDeclaredField("IMPL_LOOKUP");
        	field.setAccessible(true);
        	final Lookup lookup = (Lookup) field.get(null); // MethodHandles.lookup().in(method.getDeclaringClass())
            result = lookup
                    .unreflectSpecial(method, method.getDeclaringClass())
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        }
        
        if (result == null) {
        	result = NULL_OBJECT;
        }
        
        Object oldResult = resultCache.putIfAbsent(method.getName(), result);
        
        if (oldResult != null) {
        	result = oldResult;
        }
        
        if (result == NULL_OBJECT) {
        	result = null;
        }
        
        return result;
    }

	public void clearCache() {
		resultCache.clear();
	}
	
	// SERIALIZATION
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        type = new ConfiguredType(Objects.requireNonNull(typeClass));
        configuration = ConfigurationProvider.getConfiguration();
    }
    
}