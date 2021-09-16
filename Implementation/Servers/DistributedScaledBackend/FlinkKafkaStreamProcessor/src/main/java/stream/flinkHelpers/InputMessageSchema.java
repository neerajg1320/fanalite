package stream.flinkHelpers;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import stream.models.InputMessage;

import java.io.IOException;

public class InputMessageSchema implements DeserializationSchema<InputMessage>, SerializationSchema<InputMessage> {
    ObjectMapper objectMapper;

    @Override
    public byte[] serialize(InputMessage inputMessage) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        String error = "";
        try {
            return objectMapper.writeValueAsBytes(inputMessage);
        } catch (JsonProcessingException e) {
            error = e.getMessage();
        }

        return error.getBytes();
    }


    @Override
    public InputMessage deserialize(byte[] bytes) throws IOException {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        return objectMapper.readValue(bytes, InputMessage.class);
    }

    @Override
    public boolean isEndOfStream(InputMessage inputMessage) {
        return false;
    }

    @Override
    public TypeInformation<InputMessage> getProducedType() {
        return TypeInformation.of(InputMessage.class);
    }
}
