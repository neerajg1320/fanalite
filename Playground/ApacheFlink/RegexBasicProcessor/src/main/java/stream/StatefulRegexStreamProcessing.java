package stream;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import stream.regex.RegexEngine;
import stream.regex.RegexMatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatefulRegexStreamProcessing
{
    public static void main(String[] args) throws Exception
    {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final ParameterTool params = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> input = env.socketTextStream("localhost", Integer.parseInt(params.get("port")));
        DataStream<Tuple2<String, String>> text = input.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String value) throws Exception {
                // Split only on first occurrence of comma
                String[] parts = value.split(",", 2);
                return new Tuple2<>(parts[0], parts[1]);
            }
        });

        DataStream<Tuple3<String, String, Map<String,String>>> str =  text
                .flatMap(new FlatTokenizer());

        str.print();

        str.writeAsText(params.get("output"));


        // execute program
        env.execute("Filter Using Regular Expression");
    }


    public static final class FlatTokenizer implements FlatMapFunction<
            Tuple2<String, String>,
            Tuple3<String, String, Map<String,String>>
                    > {

        final String dateGroupName = "on";
        final String dateRegex = "\\d{2}/\\d{2}/\\d{2,4}";
        final String numberGroupName = "price";
        final String numberRegex = "\\d+";

        private String createSwipeRegex() {
            return String.format(".*(?<%s>%s).*?(?<%s>%s).*",
                    dateGroupName, dateRegex, numberGroupName, numberRegex);
        }

        private String createStockRegex() {
            return String.format(".*(?<%s>%s).*?(?<%s>%s).*?(?<%s>%s).*",
                    dateGroupName, dateRegex, "settle", dateRegex, numberGroupName, numberRegex);
        }

        @Override
        public void flatMap(Tuple2<String, String> value,
                            Collector<Tuple3<String, String, Map<String, String>>> out) {

            if (value.f0.equals("Text")) {
                RegexEngine regexEngine = new RegexEngine();

                regexEngine.addRegex("Swipe", createSwipeRegex());
                regexEngine.addRegex("Stock", createStockRegex());

                List<RegexMatch> matches = regexEngine.process(value.f1);
                for (RegexMatch match : matches) {
                    out.collect(new Tuple3<>(match.getRegexName(), match.getFullMatch(), match.getGroupMap()));
                }
            } else if (value.f0.equals("Rule")) {
                out.collect(new Tuple3<>("New Rule", value.f1, new HashMap<>()));
            } else {
                out.collect(new Tuple3<>("None", value.f0 + ":::" + value.f1, new HashMap<>()));
            }
        }

    }
}