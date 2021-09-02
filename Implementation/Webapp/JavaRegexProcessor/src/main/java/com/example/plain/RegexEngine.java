package com.example.plain;

import com.example.LogSimple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEngine {
    private static Pattern getPattern(String regexStr) {
        Pattern p = null;

        try {
            p = Pattern.compile(regexStr);
        } catch (IllegalArgumentException e) {
            //[TODO] Log
            LogSimple.log(String.format("Invalid regex string: %s", regexStr));
        }

        return p;
    }

    public static boolean isValidRegex(String regexStr) {
        //[TODO] Error check for null and empty string

        return getPattern(regexStr) != null;
    }

    public static MatchInfo apply(String regexStr, String inputStr) {
        Pattern p = getPattern(regexStr);
        List<String> matches = null;

        if (p != null) {
            Matcher m = p.matcher(inputStr);
            matches = new ArrayList<>();

            while (m.find()) {
                matches.add(m.group());
            }
        }

        int count = matches != null ? matches.size() : 0;
        return new MatchInfo(count, matches);

    }
}
