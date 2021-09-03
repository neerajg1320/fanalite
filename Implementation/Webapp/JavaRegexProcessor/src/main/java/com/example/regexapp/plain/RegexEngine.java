package com.example.regexapp.plain;

import com.example.regexapp.LogSimple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEngine {
    private static RegexValidityInfo getValidityInfo(String regexStr) {
        RegexValidityInfo validityInfo = new RegexValidityInfo();

        try {
            validityInfo.pattern = Pattern.compile(regexStr);
            validityInfo.valid = true;
        } catch (IllegalArgumentException e) {
            //[TODO] Log
            LogSimple.log(String.format("Invalid regex string: %s", e.getMessage()));
            validityInfo.valid = false;
            validityInfo.error = e.getMessage();
        }

        return validityInfo;
    }

    public static RegexValidityInfo checkValidity(String regexStr) {
        //[TODO] Error check for null and empty strin
        return getValidityInfo(regexStr);
    }

    public static RegexApplyInfo apply(String regexStr, String inputStr) {
        RegexValidityInfo validityInfo = getValidityInfo(regexStr);
        RegexApplyInfo applyInfo = new RegexApplyInfo();

        if (validityInfo.isValid()) {
            Pattern p = validityInfo.pattern;
            List<String> matches = null;

            if (p != null) {
                Matcher m = p.matcher(inputStr);
                matches = new ArrayList<>();

                while (m.find()) {
                    matches.add(m.group());
                }
            }
            applyInfo.count = matches != null ? matches.size() : 0;
            applyInfo.matches = matches;
        } else {
            applyInfo.error = validityInfo.getError();
        }

        return applyInfo;
    }
}
