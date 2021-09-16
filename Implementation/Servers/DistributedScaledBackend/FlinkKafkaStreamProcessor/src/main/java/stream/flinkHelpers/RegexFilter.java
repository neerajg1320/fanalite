package stream.flinkHelpers;

import org.apache.flink.api.common.functions.FilterFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFilter implements FilterFunction<String> {
    private List<String> regexList;
    private List<Pattern> patternList;

    public RegexFilter() {
        regexList = new ArrayList<>();

        regexList.add("\\d{2}/\\d{2}/\\d{2,4}");      // date
        regexList.add("\\d+");                      // natural numbers

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
