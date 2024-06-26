package stream;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import stream.regex.RegexEngine;
import stream.regex.RegexFactory;
import stream.regex.RegexMatch;

import java.util.List;
import java.util.Map;


public class HardcodedRegexStreamProcessing
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


    public static final class FlatTokenizer implements FlatMapFunction<String, Tuple3<String, String, Map<String,String>>> {
        @Override
        public void flatMap(String value, Collector<Tuple3<String, String, Map<String,String>>> out) {
            RegexEngine regexEngine = new RegexEngine();

            regexEngine.addRegex("Swipe", RegexFactory.createSwipeRegex());
            regexEngine.addRegex("Stock", RegexFactory.createStockRegex());

            List<RegexMatch> matches =  regexEngine.process(value);
            for (RegexMatch match: matches) {
                out.collect(new Tuple3<>(match.getRegexName(), match.getFullMatch(), match.getGroupMap()));
            }

        }

    }
}