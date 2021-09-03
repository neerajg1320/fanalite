package com.example.regexapp.regexController;

import com.example.regexapp.plain.RegexApplyInfo;

public class RegexApplyResponseBody {
    RegexApplyInfo matches;

    public RegexApplyResponseBody(RegexApplyInfo matches) {
        this.matches = matches;
    }

    public RegexApplyInfo getMatches() {
        return matches;
    }

    public void setMatches(RegexApplyInfo matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "RegexApplyResponseBody{" +
                "matches=" + matches +
                '}';
    }
}
