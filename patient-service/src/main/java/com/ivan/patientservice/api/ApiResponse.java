package com.ivan.patientservice.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // don’t serialize null fields
public class ApiResponse<T> {

    private int status;
    private boolean success;
    private T data;
    private ApiErrorResponse error;
    private Meta meta;

    // ✅ Constructors for success and error
    public ApiResponse(int status, boolean success, T data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }

    public ApiResponse(int status, boolean success, ApiErrorResponse error) {
        this.status = status;
        this.success = success;
        this.error = error;
    }

    public ApiResponse(int status, boolean success, T data, Meta meta) {
        this.status = status;
        this.success = success;
        this.data = data;
        this.meta = meta;
    }

}
