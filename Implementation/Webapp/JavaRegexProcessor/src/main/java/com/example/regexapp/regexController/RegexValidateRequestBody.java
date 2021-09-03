package com.example.regexapp.regexController;

public class RegexValidateRequestBody {
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
