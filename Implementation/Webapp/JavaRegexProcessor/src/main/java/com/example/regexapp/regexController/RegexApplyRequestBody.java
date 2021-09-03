package com.example.regexapp.regexController;

public class RegexApplyRequestBody {
    String regex;
    String text;

    public String getRegex() {
        return regex;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "RegexApplyRequestBody{" +
                "regex='" + regex + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
