package com.example.regexapp.regexController;

public class RegexValidateResponseBody {
    boolean isValid;

    public RegexValidateResponseBody(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
