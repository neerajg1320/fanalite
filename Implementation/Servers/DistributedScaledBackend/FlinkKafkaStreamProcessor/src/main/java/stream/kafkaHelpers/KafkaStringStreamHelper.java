package stream.kafkaHelpers;


import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import stream.flinkHelpers.CustomStringSchema;

import java.util.Properties;

public class KafkaStringStreamHelper {
    public static FlinkKafkaConsumer011<String> createStringConsumerForTopic(
            String topic, String kafkaAddress, String kafkaGroup

    ) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        if (kafkaGroup != null && !kafkaGroup.equals("")) {
            props.setProperty("group.id", kafkaGroup);
        }
        return new FlinkKafkaConsumer011<String>(topic, new CustomStringSchema(), props);

    }

    public static FlinkKafkaProducer011<String> createStringProducerforTopic(
            String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<String>(kafkaAddress, topic, new CustomStringSchema());
    }
}
