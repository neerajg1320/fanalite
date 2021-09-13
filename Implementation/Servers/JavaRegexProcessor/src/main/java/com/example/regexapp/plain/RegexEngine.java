package com.example.regexapp.plain;

import com.example.regexapp.LogSimple;
import com.example.regexapp.regexModels.RegexApplyResponse;
import com.example.regexapp.regexModels.RegexValidityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEngine {
    private static RegexValidityResponse getValidityInfo(String regexStr) {
        RegexValidityResponse validityInfo = new RegexValidityResponse();

        try {
            validityInfo.setPattern(Pattern.compile(regexStr));
            validityInfo.setValid(true);
        } catch (IllegalArgumentException e) {
            //[TODO] Log
            LogSimple.log(String.format("Invalid regex string: %s", e.getMessage()));
            validityInfo.setValid(false);
            validityInfo.setRegexError(e.getMessage());
        }

        return validityInfo;
    }

    public static RegexValidityResponse checkValidity(String regexStr) {
        //[TODO] Error check for null and empty strin
        return getValidityInfo(regexStr);
    }

    public static RegexApplyResponse apply(String regexStr, String inputStr) {
        RegexValidityResponse validityInfo = getValidityInfo(regexStr);
        RegexApplyResponse applyInfo = new RegexApplyResponse();

        if (validityInfo.isValid()) {
            Pattern p = validityInfo.getPattern();
            List<String> matches = null;

            if (p != null) {
                Matcher m = p.matcher(inputStr);
                matches = new ArrayList<>();

                while (m.find()) {
                    matches.add(m.group());
                }
            }
            applyInfo.setCount(matches != null ? matches.size() : 0);
            applyInfo.setMatches(matches);
        } else {
            applyInfo.setCount(-1);
            applyInfo.setRegexError(validityInfo.getRegexError());
        }

        return applyInfo;
    }
}
