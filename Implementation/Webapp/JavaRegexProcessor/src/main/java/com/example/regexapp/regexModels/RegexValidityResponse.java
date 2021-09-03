package com.example.regexapp.regexModels;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.regex.Pattern;

public class RegexValidityResponse {
    boolean valid;
    String error;

    @JsonIgnore
    Pattern pattern;

    public RegexValidityResponse() {
    }

    public RegexValidityResponse(boolean valid, String error) {
        this.valid = valid;
        this.error = error;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
                ", error='" + error + '\'' +
                '}';
    }
}
