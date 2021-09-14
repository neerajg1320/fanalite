package stream;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import stream.regex.RegexPlus;
import stream.regex.RegexUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexStreamProcessing
{



    public static void main(String[] args) throws Exception
    {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final ParameterTool params = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> text = env.socketTextStream("localhost", Integer.parseInt(params.get("port")));

        DataStream<Tuple3<String, String, Map<String,String>>> str =  text
                .flatMap(new FlatTokenizer());

        str.print();

        str.writeAsText(params.get("output"));


        // execute program
        env.execute("Filter Using Regular Expression");
    }


    public static class RegexEngine {
        private Map<String, RegexPlus> regexPlusMap;

        public RegexEngine() {
            regexPlusMap = new HashMap<>();
        }

        public void addRegex(String regexName, String regexStr) {
            regexPlusMap.put(regexName, new RegexPlus(regexStr));
        }

        public List<Tuple3<String, String, Map<String,String>>> process(String value) {
            List<Tuple3<String, String, Map<String,String>>> results = new ArrayList<>();

            for (Map.Entry<String, RegexPlus> regexPlusEntry: regexPlusMap.entrySet()) {
                String regexName = regexPlusEntry.getKey();

                Pattern p = regexPlusEntry.getValue().getPattern();
                Matcher m = p.matcher(value);

                while (m.find()) {
                    Map<String, String> groupMap = new HashMap<>();

                    List<String> groups = regexPlusEntry.getValue().getGroups();
                    for (String groupName: groups) {
                        groupMap.put(groupName, m.group(groupName));
                    }
                    results.add(new Tuple3<>(regexName, m.group(), groupMap));
                }
            }

            return results;
        }
    }


    public static final class FlatTokenizer implements FlatMapFunction<String, Tuple3<String, String, Map<String,String>>> {
        @Override
        public void flatMap(String value, Collector<Tuple3<String, String, Map<String,String>>> out) {
            RegexEngine regexEngine = new RegexEngine();

            regexEngine.addRegex("Swipe", createSwipeRegex());
            regexEngine.addRegex("Stock", createStockRegex());

            List<Tuple3<String, String, Map<String,String>>> results =  regexEngine.process(value);

            for (Tuple3<String, String, Map<String,String>> entry: results) {
                out.collect(entry);
            }
        }

        final String dateGroupName = "on";
        final String dateRegex = "\\d{2}/\\d{2}/\\d{2,4}";
        final String numberGroupName = "price";
        final String numberRegex = "\\d+";

        private String createSwipeRegex() {
            return String.format("(?<%s>%s).*(?<%s>%s).*",
                    dateGroupName, dateRegex, numberGroupName, numberRegex);
        }

        private String createStockRegex() {
            return String.format("(?<%s>%s).*(?<%s>%s).*(?<%s>%s).*",
                    dateGroupName, dateRegex, "settle", dateRegex, numberGroupName, numberRegex);
        }
    }
}