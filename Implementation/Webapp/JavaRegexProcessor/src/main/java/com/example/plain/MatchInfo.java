package com.example.plain;

import java.util.List;

public class MatchInfo {
    int count;
    List<String> matches;

    public MatchInfo(int count, List<String> matches) {
        this.count = count;
        this.matches = matches;
    }

    public int getCount() {
        return count;
    }

    public List<String> getMatches() {
        return matches;
    }
}
