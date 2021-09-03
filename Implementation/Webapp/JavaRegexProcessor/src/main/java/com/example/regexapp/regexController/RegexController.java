package com.example.regexapp.regexController;

import com.example.regexapp.LogSimple;
import com.example.regexapp.plain.MatchInfo;
import com.example.regexapp.plain.RegexEngine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
