# Terminal 1:
cd ~/Projects/Kafka/kafka_2.13-2.8.0
bin/kafka-console-producer.sh --topic text  --bootstrap-server localhost:9092

# Terminal 2:
cd ~/Projects/Kafka/kafka_2.13-2.8.0
bin/kafka-console-consumer.sh --topic transactions  --from-beginning --bootstrap-server localhost:9092

# Terminal 3:
cd /home/neeraj/mac_project/fanalite/Implementation/Servers/DistributedScaledBackend/FlinkKafkaStreamProcessor
~/Projects/Flink/flink-1.5.0/bin/flink run ./target/FlinkKafkaStreamProcessor-0.1-jar-with-dependencies.jar --kafka-host localhost:9092  --input-topic text --output-topic transactions

