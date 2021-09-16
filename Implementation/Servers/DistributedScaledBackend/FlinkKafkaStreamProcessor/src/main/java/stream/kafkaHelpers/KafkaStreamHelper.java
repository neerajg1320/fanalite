package stream.kafkaHelpers;


import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import stream.flinkHelpers.InputMessageSchema;

import java.util.Properties;

public class KafkaStreamHelper {
    public static FlinkKafkaConsumer011<String> createStringConsumerForTopic(
            String topic, String kafkaAddress, String kafkaGroup

    ) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        if (kafkaGroup != null && !kafkaGroup.equals("")) {
            props.setProperty("group.id", kafkaGroup);
        }
        return new FlinkKafkaConsumer011<String>(topic, new SimpleStringSchema(), props);

    }

    public static FlinkKafkaProducer011<String> createStringProducerforTopic(
            String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<String>(kafkaAddress, topic, new SimpleStringSchema());
    }
}
