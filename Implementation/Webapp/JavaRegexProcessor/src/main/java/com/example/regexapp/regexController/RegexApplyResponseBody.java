package com.example.regexapp.regexController;

import com.example.regexapp.plain.MatchInfo;

public class RegexApplyResponseBody {
    MatchInfo matches;

    public RegexApplyResponseBody(MatchInfo matches) {
        this.matches = matches;
    }

    public MatchInfo getMatches() {
        return matches;
    }

    public void setMatches(MatchInfo matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "RegexApplyResponseBody{" +
                "matches=" + matches +
                '}';
    }
}
