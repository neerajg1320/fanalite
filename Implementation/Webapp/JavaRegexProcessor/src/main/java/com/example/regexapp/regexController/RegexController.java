package com.example.regexapp.regexController;

import com.example.regexapp.LogSimple;
import com.example.regexapp.plain.MatchInfo;
import com.example.regexapp.plain.RegexEngine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class RegexController {

    @PostMapping("/regex/validate")
    public ResponseEntity<Boolean> regexValidate(@Valid @RequestBody RegexValidateRequestBody body) {

        LogSimple.log(body.toString());

        return new ResponseEntity<>(RegexEngine.isValidRegex(body.regex), HttpStatus.OK);
    }


    @PostMapping("/regex/apply")
    public ResponseEntity<MatchInfo>  regexApply(@Valid @RequestBody RegexApplyRequestBody body) {
        //[TODO] Need to be replaced with Log4j
        LogSimple.log(body.toString());

        return new ResponseEntity<>(RegexEngine.apply(body.regex, body.text), HttpStatus.OK);
    }

}
