package tech.lapsa.eurasia.ws.rs.app;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import tech.lapsa.eurasia.ws.rs.provider.JacksonObjectMapperProvider;
import tech.lapsa.payara.jersey.localization.ValidationMessagesLocalizationProvider;

@ApplicationPath("")
public class ApplicationConfig extends Application {

    private final Set<Class<?>> classes;

    private final Map<String, Object> properties;

    public ApplicationConfig() {

	classes = Stream.<Class<?>> builder() //
		.add(JacksonFeature.class) //
		.add(JacksonObjectMapperProvider.class) //
		.add(RolesAllowedDynamicFeature.class) //
		.add(ValidationMessagesLocalizationProvider.class) //

		// permitted
		.add(POSWS.class) //

		//
		.build() //
		.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));

	properties = Stream.<Map.Entry<String, Object>> builder() //
		.add(immutableEntry(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)) //
		.add(immutableEntry(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true)) //
		.add(immutableEntry(ServerProperties.APPLICATION_NAME, "Eurasia WS API")) //
		.add(immutableEntry(ServerProperties.TRACING, TracingConfig.ON_DEMAND.toString())) //
		.add(immutableEntry(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true)) //
		.build() //
		.collect(Collectors.collectingAndThen( //
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
			Collections::unmodifiableMap) //
	);

    }

    @Override
    public Set<Class<?>> getClasses() {
	return classes;
    }

    @Override
    public Map<String, Object> getProperties() {
	return properties;
    }

    // PRIVATE

    private static <K, V> Map.Entry<K, V> immutableEntry(final K key, final V value) {
	return new Map.Entry<K, V>() {
	    @Override
	    public K getKey() {
		return key;
	    }

	    @Override
	    public V getValue() {
		return value;
	    }

	    @Override
	    public V setValue(final V value) {
		throw new UnsupportedOperationException();
	    }
	};
    }

}
