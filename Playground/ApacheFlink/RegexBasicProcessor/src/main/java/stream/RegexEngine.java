package stream;

import org.apache.flink.api.java.tuple.Tuple3;
import stream.regex.RegexPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEngine {
    private Map<String, RegexPlus> regexPlusMap;

    public RegexEngine() {
        regexPlusMap = new HashMap<>();
    }

    public void addRegex(String regexName, String regexStr) {
        regexPlusMap.put(regexName, new RegexPlus(regexStr));
    }

    public void removeRegex(String regexName) {
        regexPlusMap.remove(regexName);
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
