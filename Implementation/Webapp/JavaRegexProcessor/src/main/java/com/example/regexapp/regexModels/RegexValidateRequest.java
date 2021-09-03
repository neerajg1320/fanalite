package com.example.regexapp.regexModels;

import javax.validation.constraints.NotBlank;

public class RegexValidateRequest {
    @NotBlank(message = "regex is missing")
    String regex;

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "RegexValidateRequest{" +
                "regex='" + regex + '\'' +
                '}';
    }
}
