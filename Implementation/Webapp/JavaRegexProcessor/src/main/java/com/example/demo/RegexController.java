package com.example.demo;

import com.example.LogSimple;
import com.example.plain.MatchInfo;
import com.example.plain.RegexEngine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegexController {

    @GetMapping("/regex/validate")
    public boolean regexValidate(@RequestParam(value = "regex", defaultValue = "") String regexStr) {
        //[TODO] Need to be replaced with Log4j
        LogSimple.log(String.format("regex=%s", regexStr));

        return RegexEngine.isValidRegex(regexStr);
    }


    @GetMapping("/regex/apply")
    public MatchInfo regexApply(@RequestParam(value = "regex", defaultValue = "") String regexStr,
                                @RequestParam(value = "input", defaultValue = "") String inputStr) {
        //[TODO] Need to be replaced with Log4j
        LogSimple.log(String.format("regex=%s", regexStr));

        return RegexEngine.apply(regexStr, inputStr);
    }
}
