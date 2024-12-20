package com.example.DynamicPricingAPI.model;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String errorCode;
    private String message;
    private Object details;
    private String path;

    public ErrorResponse(){}
    public ErrorResponse(LocalDateTime timestamp, int status, String errorCode, String message, Object details, String path){
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.details = details;
    }

    public ErrorResponse(int status, String message){
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
