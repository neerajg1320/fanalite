package stream;

import java.util.List;
import java.util.regex.Pattern;

public class RegexPlus {
    private String regexStr;
    private Pattern pattern;
    private List<String> groups;

    public RegexPlus(String regexStr) {
        this.regexStr = regexStr;
        pattern = Pattern.compile(regexStr);
        groups = RegexUtil.getGroups(regexStr);
    }

    public List<String> getGroups() {
        return groups;
    }

    public String getRegexStr() {
        return regexStr;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
