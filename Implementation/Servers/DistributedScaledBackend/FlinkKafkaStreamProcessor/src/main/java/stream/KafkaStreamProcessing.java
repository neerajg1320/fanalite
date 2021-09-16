package stream;



import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;
import stream.kafkaHelpers.KafkaStringStreamHelper;
import stream.regex.RegexEngine;
import stream.regex.RegexMatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KafkaStreamProcessing
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


        FlinkKafkaConsumer011<String> kafkaConsumer = KafkaStringStreamHelper.createStringConsumerForTopic(
                kafkaInputTopic, kafkaAddress, kafkaInputGroup);


        DataStream<String> inputStrStream = env.addSource(kafkaConsumer);
        DataStream<Tuple4<Long, String, String, String>> inputTupleStream = inputStrStream.map(new MapFunction<String, Tuple4<Long, String, String, String>>() {
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

        DataStream<Tuple3<String, String, Map<String,String>>> matchesStream =  inputTupleStream
                .keyBy(0)
                .flatMap(new StatefulRegexProcessor());


        matchesStream
                .map(new MapFunction<Tuple3<String, String, Map<String, String>>, String>() {
                      @Override
                      public String map(Tuple3<String, String, Map<String, String>> value) {
                          return value.f0 + ", " + value.f1 + ", " + value.f2;
                      }
                  }
                ).addSink(KafkaStringStreamHelper.createStringProducerforTopic(kafkaOutputTopic, kafkaAddress));

        // execute program
        env.execute("Filter Using Regular Expression");
    }


    public static class StatefulRegexProcessor extends RichFlatMapFunction<
            Tuple4<Long, String, String, String>,
            Tuple3<String, String, Map<String,String>>
            > {

        private transient ListState<Tuple2<String, String>> regexListState;
        private transient RegexEngine regexEngine;

        @Override
        public void flatMap(Tuple4<Long, String, String, String> value,
                            Collector<Tuple3<String, String, Map<String, String>>> out) throws Exception {
            final String selector = value.f1.trim();
            final String name = value.f2.trim();
            final String str = value.f3.trim();

            if (regexEngine == null) {
                out.collect(new Tuple3<>("RegexEngine:", "Initializing", new HashMap<>()));
                initRegexEngine(out);
            }

            if (selector.equals("Text")) {
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
                regexListState.add(new Tuple2<>(name, str));
                regexEngine.addRegex(name, str);
            } else {
                out.collect(new Tuple3<>("Not Processed", selector + ":::" + name, new HashMap<>()));
            }
        }

        private void initRegexEngine(Collector<Tuple3<String, String, Map<String, String>>> out) throws Exception {
            regexEngine = new RegexEngine();
            
            // regexListState.get() has to be called from keyed Context, hence it cannot be called in open()
            for (Tuple2<String, String> regexTuple : regexListState.get()) {
                regexEngine.addRegex(regexTuple.f0, regexTuple.f1);
                if (out != null) {
                    out.collect(new Tuple3<>("FromState:", regexTuple.f1, new HashMap<>()));
                }
            }
        }

        public void open(Configuration conf) throws Exception {
            ListStateDescriptor<Tuple2<String,String>> listDesc = new ListStateDescriptor<>(
                    "regexStrList",
                    TypeInformation.of(new TypeHint<Tuple2<String, String>>() {})
            );
            regexListState = getRuntimeContext().getListState(listDesc);
        }
    }
}