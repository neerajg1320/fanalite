package com.example.regexapp.regexController;

import com.example.regexapp.LogSimple;
import com.example.regexapp.plain.*;
import com.example.regexapp.regexModels.RegexApplyRequest;
import com.example.regexapp.regexModels.RegexApplyResponse;
import com.example.regexapp.regexModels.RegexValidateRequest;
import com.example.regexapp.regexModels.RegexValidityResponse;
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
    public ResponseEntity createResponseEntity(Object result, Object error) {
        HttpStatus httpStatus = HttpStatus.OK;

        RespBody respBody = new RespBody();

        if (error != null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respBody.setStatus(ResponseEnum.FAILED.toString());
            respBody.setError(error);
        } else {
            respBody.setStatus(ResponseEnum.SUCCESS.toString());
            respBody.setResult(result);
        }

        return new ResponseEntity(respBody, httpStatus);
    }

    @PostMapping("/regex/validate")
    public ResponseEntity<RespBody> regexValidate(@Valid @RequestBody RegexValidateRequest body) {
        LogSimple.log(body.toString());

        RegexValidityResponse validityInfo = RegexEngine.checkValidity(body.getRegex());
        return createResponseEntity(validityInfo, null);
    }

    @PostMapping("/regex/apply")
    public ResponseEntity<RegexApplyResponse>  regexApply(@Valid @RequestBody RegexApplyRequest body) {
        LogSimple.log(body.toString());

        RegexApplyResponse regexApplyResponse = RegexEngine.apply(body.getRegex(), body.getText());
        return createResponseEntity(regexApplyResponse, regexApplyResponse.getRegexError());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return createResponseEntity(null, errors);
    }
}
