package com.example.demo;

import com.example.plain.RegexEngine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegexController {

    @GetMapping("/regex/validate")
    public String regexValidate(@RequestParam(value = "regex", defaultValue = "") String regexStr) {
        //[TODO] Need to be replaced with Log4j
        System.out.println(String.format("regex=%s", regexStr));

        boolean result = RegexEngine.isValidRegex(regexStr);
        return String.format("result: %s", result);
    }


    @GetMapping("/regex/apply")
    public MatchInfo regexApply(@RequestParam(value = "regex", defaultValue = "") String regexStr,
                             @RequestParam(value = "input", defaultValue = "") String inputStr) {
        //[TODO] Need to be replaced with Log4j
        System.out.println(String.format("regex=%s", regexStr));

        List<String> matches = RegexEngine.apply(regexStr, inputStr);
        int count = matches != null ? matches.size() : 0;

        //return String.format("%d matches found", count);

        MatchInfo matchInfo = new MatchInfo(count, matches);
        return matchInfo;
    }
}
