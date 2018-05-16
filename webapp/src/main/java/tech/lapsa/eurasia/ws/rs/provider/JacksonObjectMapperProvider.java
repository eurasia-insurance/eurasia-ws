package tech.lapsa.eurasia.ws.rs.provider;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {
    ObjectMapper mapper;

    @SuppressWarnings("deprecation")
    public JacksonObjectMapperProvider() {

	mapper = new ObjectMapper();

	mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
	mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);

	mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, true);
	mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
	mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
	mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

	mapper.configure(Feature.ALLOW_COMMENTS, true);

	mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));

    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
	return mapper;
    }
}