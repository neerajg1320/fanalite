package com.example.regexapp.regexModels;

import java.util.List;

public class RegexApplyResponse {
    int count;
    List<String> matches;
    String error;

    public RegexApplyResponse() {
    }

    public RegexApplyResponse(int count, List<String> matches, String error) {
        this.count = count;
        this.matches = matches;
        this.error = error;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RegexApplyResponse{" +
                "count=" + count +
                ", matches=" + matches +
                ", error='" + error + '\'' +
                '}';
    }
}
