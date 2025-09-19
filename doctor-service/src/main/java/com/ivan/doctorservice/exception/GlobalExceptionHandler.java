package com.ivan.doctorservice.exception;

import com.ivan.doctorservice.api.ApiErrorResponse;
import com.ivan.doctorservice.api.ApiResponse;
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

    // 1️⃣ Handle validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed",
                fieldErrors.toString()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                false,
                errorResponse
        );

        return ResponseEntity.badRequest().body(response);
    }

    // 2️⃣ Handle duplicate email / unique constraint violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Data integrity violation occurred.";
        String code = "DUPLICATE_DATA";

        // check if it's email constraint
        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("email")) {
            message = "Duplicate email found. Please use another email.";
            code = "DUPLICATE_EMAIL";
        }

        ApiErrorResponse errorResponse = new ApiErrorResponse(code, message, ex.getMostSpecificCause().getMessage());

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                false,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 3️⃣ Handle not found exceptions
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlePatientNotFound(PatientNotFoundException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "PATIENT_NOT_FOUND",
                ex.getMessage(),
                "Check the patient ID and try again."
        );

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                false,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 4️⃣ Handle IllegalArgumentException (e.g., service-level checks)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "INVALID_ARGUMENT",
                ex.getMessage(),
                "Check your request payload and try again."
        );

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                false,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 5️⃣ Catch-all for any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                "Unexpected error occurred."
        );

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                false,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmail(DuplicateEmailException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "DUPLICATE_EMAIL",
                ex.getMessage(),
                "Patient with this email already exists."
        );
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                false,
                errorResponse
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
