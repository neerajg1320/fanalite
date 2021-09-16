package stream.flinkHelpers;


import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import stream.models.InputMessage;

import java.io.IOException;

public class InputMessageDeserializationSchema implements DeserializationSchema<InputMessage> {
    static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public InputMessage deserialize(byte[] bytes) throws IOException {
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
