package stream;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.util.Collector;
import stream.regex.RegexEngine;
import stream.regex.RegexMatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class KafkaStreamProcessing
{
    public static void main(String[] args) throws Exception
    {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final ParameterTool params = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(params);

        FlinkKafkaConsumer011<String> kafkaConsumer = createStringConsumerForTopic(
                "rules-transactions", "localhost:9092", "flink");
        // DataStream<String> input = env.socketTextStream("localhost", Integer.parseInt(params.get("port")));

        DataStream<String> input = env.addSource(kafkaConsumer);
        DataStream<Tuple4<Long, String, String, String>> text = input.map(new MapFunction<String, Tuple4<Long, String, String, String>>() {
            final int numParams = 4;
            @Override
            public Tuple4<Long, String, String, String> map(String value) throws Exception {
                // Split only on first occurrence of comma
                String[] parts = value.split(",", numParams);
                if (parts.length < numParams) {
                    return new Tuple4<>(-1L, String.format("Minimum %d elements required", numParams), "", "");
                }

                Long userId = -1L;
                try {
                    userId = Long.parseLong(parts[0]);
                } catch (Exception e) {
                    // Check how to print log messages
                }
                return new Tuple4<>(userId, parts[1], parts[2], parts[3]);
            }
        });

        DataStream<Tuple3<String, String, Map<String,String>>> str =  text
                .keyBy(0)
                .flatMap(new StatefulRegexProcessor());

        str.print();

        str.writeAsText(params.get("output"));


        // execute program
        env.execute("Filter Using Regular Expression");
    }

    public static FlinkKafkaConsumer011<String> createStringConsumerForTopic(
            String topic, String kafkaAddress, String kafkaGroup

    ) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id", kafkaGroup);
        return new FlinkKafkaConsumer011<String>(topic, new SimpleStringSchema(), props);

    }

    public static FlinkKafkaProducer011<String> createStringProducerforTopic(
            String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<String>(kafkaAddress, topic, new SimpleStringSchema());
    }


    public static class StatefulRegexProcessor extends RichFlatMapFunction<
            Tuple4<Long, String, String, String>,
            Tuple3<String, String, Map<String,String>>
            > {

        private transient ListState<String> regexListState;
//        private transient RegexEngine regexEngine = new RegexEngine();

        @Override
        public void flatMap(Tuple4<Long, String, String, String> value,
                            Collector<Tuple3<String, String, Map<String, String>>> out) throws Exception {
            final String selector = value.f1.trim();
            final String name = value.f2.trim();
            final String str = value.f3.trim();

            if (selector.equals("Text")) {
                RegexEngine regexEngine = new RegexEngine();

                int index = 0;
                for (String regexStr: regexListState.get()) {
                    regexEngine.addRegex(String.format("Regex%d", index++), regexStr);
                    out.collect(new Tuple3<>("State:" , regexStr, new HashMap<>()));
                }

                List<RegexMatch> matches = regexEngine.process(str);

                if (matches.size() <= 0) {
                    out.collect(new Tuple3<>("No Match:" + name, str, new HashMap<>()));
                } else {
                    for (RegexMatch match : matches) {
                        out.collect(new Tuple3<>(match.getRegexName(), match.getFullMatch(), match.getGroupMap()));
                    }
                }
            } else if (selector.equals("Rule")) {
                out.collect(new Tuple3<>("New Rule:" + name, str, new HashMap<>()));
                regexListState.add(str);
            } else {
                out.collect(new Tuple3<>("Not Processed", selector + ":::" + name, new HashMap<>()));
            }
        }

        public void open(Configuration con) throws Exception {
            ListStateDescriptor<String> listDesc = new ListStateDescriptor<String>("regexStrList", String.class);
            regexListState = getRuntimeContext().getListState(listDesc);
        }
    }
}