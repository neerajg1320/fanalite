package stream;


import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;
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
                .filter(new RegexFilter())
                .map(new Tokenizer());

        str.print();

        str.writeAsText(params.get("output"));

        // execute program
        env.execute("Filter Using Regular Expression");
    }

    public static final class RegexFilter implements FilterFunction<String> {
        private List<String> regexList;
        private List<Pattern> patternList;

        public RegexFilter() {
            regexList = new ArrayList<>();

            // regexList.add("\\d+");                      // natural numbers
            regexList.add("\\d{2}/\\d{2}/\\d{2,4}");      // date

            patternList = new ArrayList<>();
            for (String regex: regexList) {
                patternList.add(Pattern.compile(regex));
            }

        }

        @Override
        public boolean filter(String value) throws Exception {
            boolean isMatch = false;
            for (int index = 0; index < patternList.size(); index++) {
                Matcher m = patternList.get(index).matcher(value);
                int count = 0;
                while (m.find()) {
                    count++;
                }

                if (count > 0) {
                    isMatch = true;
                }
            }

            return isMatch;
        }
    }

    public static final class Tokenizer implements MapFunction<String, Tuple2<String, String>> {
        public Tuple2<String, String> map(String value) {
            return new Tuple2(value, "Transaction");
        }
    }
}