package com.example.regexapp.regexController;

import javax.validation.constraints.NotBlank;

public class RegexValidateRequestBody {
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
        return "RegexValidateRequestBody{" +
                "regex='" + regex + '\'' +
                '}';
    }
}
