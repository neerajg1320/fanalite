package com.example.regexapp.regexController;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespBody<T, E> {
    String status;
    T result;
    E error;

    public RespBody() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public E getError() {
        return error;
    }

    public void setError(E error) {
        this.error = error;
    }

//    @Override
//    public String toString() {
//        return "RespBody{" +
//                "status='" + status + '\'' +
//                ", result=" + result +
//                ", error=" + error +
//                '}';
//    }
}
