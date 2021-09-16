package stream.kafkaHelpers;


import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import stream.flinkHelpers.CustomStringSchema;
import stream.flinkHelpers.PojoSchema;
import stream.models.InputMessage;

import java.util.Properties;

public class KafkaInputMessageStreamHelper {
    public static FlinkKafkaConsumer011<InputMessage> createStringConsumerForTopic(
            String topic, String kafkaAddress, String kafkaGroup

    ) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        if (kafkaGroup != null && !kafkaGroup.equals("")) {
            props.setProperty("group.id", kafkaGroup);
        }
        return new FlinkKafkaConsumer011<InputMessage>(topic, new PojoSchema<>(InputMessage.class), props);

    }

    public static FlinkKafkaProducer011<InputMessage> createStringProducerforTopic(
            String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<InputMessage>(kafkaAddress, topic, new PojoSchema<>(InputMessage.class));
    }
}
