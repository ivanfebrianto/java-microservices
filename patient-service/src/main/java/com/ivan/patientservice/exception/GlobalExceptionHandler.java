package com.ivan.patientservice.exception;

import com.ivan.patientservice.api.ApiErrorResponse;
import com.ivan.patientservice.api.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        String details = errors.toString();

        ApiErrorResponse apiError = new ApiErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed",
                details
        );

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                false,
                apiError
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("success", false);

        Map<String, String> error = new HashMap<>();
        error.put("code", "DUPLICATE_DATA");

        // check if it's the email constraint
        if (ex.getMessage().toLowerCase().contains("email")) {
            error.put("message", "Duplicate email found. Please use another email.");
        } else {
            error.put("message", "Data integrity violation occurred.");
        }

        body.put("error", error);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlePatientNotFound(PatientNotFoundException ex) {
        ApiErrorResponse error = new ApiErrorResponse("PATIENT_NOT_FOUND", ex.getMessage(), "Check the patient ID and try again.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404, false, error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage(), "Unexpected error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(500, false, error));
    }
}
