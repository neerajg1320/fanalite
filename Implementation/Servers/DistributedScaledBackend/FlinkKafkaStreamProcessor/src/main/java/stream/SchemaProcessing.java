package stream;



import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;
import stream.kafkaHelpers.KafkaInputMessageStreamHelper;
import stream.kafkaHelpers.KafkaStringStreamHelper;
import stream.models.InputMessage;
import stream.regex.RegexEngine;
import stream.regex.RegexMatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SchemaProcessing
{
    public static void main(String[] args) throws Exception
    {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final ParameterTool params = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(params);

        String kafkaAddress = params.get("kafka-address");
        String kafkaInputTopic = params.get("input-topic");
        String kafkaInputGroup = params.get("input-group");
        String kafkaOutputTopic = params.get("output-topic");

        if (kafkaAddress == null || kafkaAddress.equals("")) {
            kafkaAddress = "localhost:9092";
        }
        if (kafkaInputTopic == null || kafkaInputTopic.equals("")) {
            kafkaInputTopic = "fanalite-input";
        }
        if (kafkaOutputTopic == null || kafkaOutputTopic.equals("")) {
            kafkaOutputTopic = "fanalite-output";
        }


        FlinkKafkaConsumer011<InputMessage> kafkaConsumer = KafkaInputMessageStreamHelper.createStringConsumerForTopic(
                kafkaInputTopic, kafkaAddress, kafkaInputGroup);


        env
                .addSource(kafkaConsumer)
                .addSink(KafkaInputMessageStreamHelper.createStringProducerforTopic(kafkaOutputTopic, kafkaAddress));

        // execute program
        env.execute("Filter Using Regular Expression");
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