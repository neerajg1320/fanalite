package stream.flinkHelpers;


import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import stream.models.InputMessage;


import java.util.logging.Logger;

public class InputMessageSerializationSchema implements SerializationSchema<InputMessage> {
    ObjectMapper objectMapper;

    @Override
    public byte[] serialize(InputMessage inputMessage) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return new byte[0];
    }
}
