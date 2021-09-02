package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//[TODO] Handle exception for wrong parameter passing
// For URL:  http://192.168.1.134:8080/regex/validate?regex=ab[
// We get bad request
//

// ComponentScan is needed if APIs are defined in the class containing main()
@RestController
@ComponentScan(basePackages={"com.example"})
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @GetMapping("/info")
    public String applicationInfo() {
        return String.format("JavaRegexProcessor: Springboot Application");
    }
}
