package stream;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class WordCountStreaming
{

    public static void main(String[] args) throws Exception
    {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final ParameterTool params = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> text = env.socketTextStream("localhost", Integer.parseInt(params.get("port")));

        DataStream<Tuple2<String, Integer>> counts =  text
                .filter(new NameFilter("A"))
                .map(new Tokenizer())         // split up the lines in pairs (2-tuples) containing: tuple2 {(name,1)...}
                .keyBy(0).sum(1);            // group by the tuple field "0" and sum up tuple field "1"

        counts.print();

        counts.writeAsText(params.get("output"));

        // execute program
        env.execute("Streaming WordCount");
    }

    public static final class Tokenizer implements MapFunction<String, Tuple2<String, Integer>>
    {
        public Tuple2<String, Integer> map(String value)
        {
            return new Tuple2(value, Integer.valueOf(1));
        }
    }

    public static final class NameFilter implements FilterFunction<String> {
        private String prefix;

        public NameFilter(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public boolean filter(String value) throws Exception {
            return value.startsWith(prefix);
        }
    }
}