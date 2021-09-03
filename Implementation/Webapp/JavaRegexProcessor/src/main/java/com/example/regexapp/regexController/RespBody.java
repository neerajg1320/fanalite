package com.example.regexapp.regexController;

public class RespBody<T> {
    String status;
    T result;

    public RespBody(String status, T result) {
        this.status = status;
        this.result = result;
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

    @Override
    public String toString() {
        return "RespBody{" +
                "status='" + status + '\'' +
                ", result=" + result +
                '}';
    }
}
