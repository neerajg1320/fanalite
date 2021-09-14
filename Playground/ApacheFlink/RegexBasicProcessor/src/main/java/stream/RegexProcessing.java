package stream;


import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexProcessing
{

    public static void main(String[] args) throws Exception
    {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final ParameterTool params = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> text = env.socketTextStream("localhost", Integer.parseInt(params.get("port")));

        DataStream<Tuple2<String, String>> str =  text
                .flatMap(new FlatTokenizer());

        str.print();

        str.writeAsText(params.get("output"));

        // execute program
        env.execute("Filter Using Regular Expression");
    }


    public static class RegexEngine {
        private Map<String, String> regexMap;
        private Map<String, Pattern> patternMap;

        public RegexEngine() {
            regexMap = new HashMap<>();

            regexMap.put("Date", "\\d{2}/\\d{2}/\\d{2,4}");      // date
            regexMap.put("Number", "\\d+");                      // natural numbers

            patternMap = new HashMap<>();
            for (Map.Entry<String, String> regexEntry: regexMap.entrySet()) {
                patternMap.put(regexEntry.getKey() , Pattern.compile(regexEntry.getValue()));
            }

        }

        public List<Tuple2<String, String>> process(String value) {
            List<Tuple2<String, String>> results = new ArrayList<>();

            for (Map.Entry<String, Pattern> patternMapEntry: patternMap.entrySet()) {
                Pattern p = patternMapEntry.getValue();
                Matcher m = p.matcher(value);
                while (m.find()) {
                    results.add(new Tuple2<>(patternMapEntry.getKey(), m.group()));
                }
            }

            return results;
        }
    }

    
    public static final class FlatTokenizer implements FlatMapFunction<String, Tuple2<String, String>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, String>> out) {
            RegexEngine regexEngine = new RegexEngine();
            List<Tuple2<String, String>> results =  regexEngine.process(value);

            for (Tuple2<String, String> entry: results) {
                out.collect(entry);
            }
        }
    }
}