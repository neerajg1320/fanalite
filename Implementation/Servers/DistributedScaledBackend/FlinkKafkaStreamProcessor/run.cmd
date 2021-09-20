## Setup Environment

# Setup Flink
~/Projects/Flink/flink-1.5.0/bin/start-cluster.sh

# Setup Kafka
# We do not need to start Zookeper as it is started by the Flink start-cluster
# bin/zookeeper-server-start.sh config/zookeeper.properties
cd ~/Projects/Kafka/kafka_2.13-2.8.0
bin/kafka-server-start.sh config/server.properties


## Start Application

# Terminal 1:
# Produce messages on topic 'text'
~/Projects/Kafka/kafka_2.13-2.8.0/bin/kafka-console-producer.sh --topic text  --bootstrap-server localhost:9092

# Terminal 2:
# Subscribe to messages on topic 'text'
~/Projects/Kafka/kafka_2.13-2.8.0/bin/kafka-console-consumer.sh --topic text  --from-beginning --bootstrap-server localhost:9092

# Terminal 3:
# Start Flink application. 
# The application listen to messages on topic 'text' and produced the results on topic 'transactions'
cd /home/neeraj/mac_project/fanalite/Implementation/Servers/DistributedScaledBackend/FlinkKafkaStreamProcessor
~/Projects/Flink/flink-1.5.0/bin/flink run ./target/FlinkKafkaStreamProcessor-0.1-jar-with-dependencies.jar --kafka-host localhost:9092  --input-topic text --output-topic transactions


# Terminal 2:
# Subscribe to messages on topic 'transactions'
~/Projects/Kafka/kafka_2.13-2.8.0/bin/kafka-console-consumer.sh --topic transactions  --from-beginning --bootstrap-server localhost:9092


## Sample Input
1, Rule, CheckNumber, .*(?<Date>\d{2}/\d{2}/\d{4}).*(?<Number>\d+).*
1, Text, Payment, Dated 10/09/2020 and Rs 455 complete


## Expected Output
New Rule:CheckNumber, .*(?<Date>\d{2}/\d{2}/\d{4}).*(?<Number>\d+).*, {}
State:, .*(?<Date>\d{2}/\d{2}/\d{4}).*(?<Number>\d+).*, {}
Regex0, Dated 10/09/2020 and Rs 455 complete, {Number=5, Date=10/09/2020}
