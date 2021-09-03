package com.example.regexapp.regexController;

import com.example.regexapp.LogSimple;
import com.example.regexapp.plain.RegexApplyInfo;
import com.example.regexapp.plain.RegexEngine;
import com.example.regexapp.plain.RegexValidityInfo;
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
    public ResponseEntity createResponseEntity(Object result, String error) {
        HttpStatus httpStatus = HttpStatus.OK;

        if (error != null) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {

        }

        RespBody responseBody = new RespBody(ResponseEnum.SUCCESS.toString(), result);
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    @PostMapping("/regex/validate")
    public ResponseEntity<RespBody> regexValidate(@Valid @RequestBody RegexValidateRequestBody body) {
        LogSimple.log(body.toString());

        RegexValidityInfo validityInfo = RegexEngine.checkValidity(body.regex);
        return createResponseEntity(validityInfo, null);
    }

    @PostMapping("/regex/apply")
    public ResponseEntity<RegexApplyInfo>  regexApply(@Valid @RequestBody RegexApplyRequestBody body) {
        LogSimple.log(body.toString());

        RegexApplyInfo regexApplyInfo = RegexEngine.apply(body.regex, body.text);
        return createResponseEntity(regexApplyInfo, null);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(PatternSyntaxException.class)
//    public String handleRegexException(
//            PatternSyntaxException ex) {
//        return ex.getMessage();
//    }

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
