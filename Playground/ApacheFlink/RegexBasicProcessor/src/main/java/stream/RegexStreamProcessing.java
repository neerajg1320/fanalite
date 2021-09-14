package stream;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;


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
            return String.format("(?<%s>%s).*?(?<%s>%s).*",
                    dateGroupName, dateRegex, numberGroupName, numberRegex);
        }

        private String createStockRegex() {
            return String.format("(?<%s>%s).*?(?<%s>%s).*?(?<%s>%s).*",
                    dateGroupName, dateRegex, "settle", dateRegex, numberGroupName, numberRegex);
        }
    }
}