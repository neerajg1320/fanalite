package stream.regex;


import java.util.ArrayList;
import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtil {

    public static List<String> getGroups(String regex) {
        List<String> namedGroups = new ArrayList<>();

        Matcher m = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>").matcher(regex);

        while (m.find()) {
            namedGroups.add(m.group(1));
        }

        return namedGroups;
    }
}
