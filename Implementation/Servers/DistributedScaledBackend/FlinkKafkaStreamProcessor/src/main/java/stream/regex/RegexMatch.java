package stream.regex;

import java.util.Map;

public class RegexMatch {
    private String regexName;
    private String fullMatch;
    private Map<String, String> groupMap;

    public RegexMatch(String regexName, String fullMatch, Map<String, String> groupMap) {
        this.regexName = regexName;
        this.fullMatch = fullMatch;
        this.groupMap = groupMap;
    }

    public String getRegexName() {
        return regexName;
    }

    public String getFullMatch() {
        return fullMatch;
    }

    public Map<String, String> getGroupMap() {
        return groupMap;
    }
}
