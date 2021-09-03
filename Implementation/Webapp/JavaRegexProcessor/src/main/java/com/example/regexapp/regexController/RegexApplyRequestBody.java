package com.example.regexapp.regexController;

import javax.validation.constraints.NotBlank;

public class RegexApplyRequestBody {
    @NotBlank(message = "regex is missing")
    String regex;

    @NotBlank(message = "text is missing")
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
