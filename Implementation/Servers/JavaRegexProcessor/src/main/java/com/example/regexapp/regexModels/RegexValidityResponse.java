package com.example.regexapp.regexModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegexValidityResponse {
    boolean valid;
    String regexError;

    @JsonIgnore
    Pattern pattern;

    public RegexValidityResponse() {
    }

    public RegexValidityResponse(boolean valid, String error) {
        this.valid = valid;
        this.regexError = error;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getRegexError() {
        return regexError;
    }

    public void setRegexError(String regexError) {
        this.regexError = regexError;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "RegexValidityResponse{" +
                "valid=" + valid +
                ", regexError='" + regexError + '\'' +
                '}';
    }
}
