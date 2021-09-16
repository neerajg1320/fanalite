package stream.flinkHelpers;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonParseException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PojoSchema<T> implements SerializationSchema<T>, DeserializationSchema<T> {

    ObjectMapper objectMapper;
    Class<T> klass;

    public PojoSchema(Class<T> klass) {
        this.klass = klass;
        objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(T pojo) {
        String error;
        try {
            return objectMapper.writeValueAsBytes(pojo);
        } catch (JsonProcessingException e) {
            error = e.getMessage();
        }

        return error.getBytes();
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(bytes, klass);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isEndOfStream(T t) {
        return false;
    }



    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(klass);
    }
}
