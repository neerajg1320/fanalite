package com.example.regexapp.regexModels;

import java.util.List;

public class RegexApplyResponse {
    int count;
    List<String> matches;
    String regexError;

    public RegexApplyResponse() {
    }

    public RegexApplyResponse(int count, List<String> matches, String error) {
        this.count = count;
        this.matches = matches;
        this.regexError = error;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getMatches() {
        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }

    public String getRegexError() {
        return regexError;
    }

    public void setRegexError(String regexError) {
        this.regexError = regexError;
    }

    @Override
    public String toString() {
        return "RegexApplyResponse{" +
                "count=" + count +
                ", matches=" + matches +
                ", regexError='" + regexError + '\'' +
                '}';
    }
}
