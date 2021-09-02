package com.example.demo;

import com.example.plain.RegexEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//[TODO] Handle exception for wrong parameter passing
// For URL:  http://192.168.1.134:8080/regex/validate?regex=ab[
// We get bad request
//
@SpringBootApplication
@RestController
@ComponentScan(basePackages={"com.example"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    //[TODO] Need to be placed in its own resource file
    @GetMapping("/regex/validate")
    public String regexValidate(@RequestParam(value = "regex", defaultValue = "") String regexStr) {
        //[TODO] Need to be replaced with Log4j
        System.out.println(String.format("regex=%s", regexStr));

        boolean result = RegexEngine.isValidRegex(regexStr);
        return String.format("result: %s", result);
    }

    //[TODO] Need to be placed in its own resource file
    @GetMapping("/regex/apply")
    public String regexApply(@RequestParam(value = "regex", defaultValue = "") String regexStr,
                             @RequestParam(value = "input", defaultValue = "") String inputStr) {
        //[TODO] Need to be replaced with Log4j
        System.out.println(String.format("regex=%s", regexStr));

        List<String> matches = RegexEngine.apply(regexStr, inputStr);
        int count = matches != null ? matches.size() : 0;
        return String.format("%d matches found", count);
    }
}
