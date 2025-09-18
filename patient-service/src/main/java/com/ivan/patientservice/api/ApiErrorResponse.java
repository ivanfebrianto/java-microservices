package com.ivan.patientservice.api;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private String code;
    private String message;
    private String details;

    public ApiErrorResponse(String code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
