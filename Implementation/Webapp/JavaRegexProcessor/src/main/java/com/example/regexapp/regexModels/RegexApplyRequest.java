package com.example.regexapp.regexModels;

import javax.validation.constraints.NotBlank;

public class RegexApplyRequest {
    @NotBlank(message = "'regex' is a required property")
    String regex;

    @NotBlank(message = "'text' is a required property")
    String text;

    public String getRegex() {
        return regex;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "RegexApplyRequest{" +
                "regex='" + regex + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
