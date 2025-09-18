package com.ivan.patientservice.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // don’t serialize null fields
public class ApiResponse<T> {

    private int status;
    private boolean isSuccess;
    private T data;
    private ApiErrorResponse error;
    private Meta meta;

    // ✅ Constructors for success and error
    public ApiResponse(int status, boolean isSuccess, T data) {
        this.status = status;
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public ApiResponse(int status, boolean isSuccess, ApiErrorResponse error) {
        this.status = status;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public ApiResponse(int status, boolean isSuccess, T data, Meta meta) {
        this.status = status;
        this.isSuccess = isSuccess;
        this.data = data;
        this.meta = meta;
    }

}
