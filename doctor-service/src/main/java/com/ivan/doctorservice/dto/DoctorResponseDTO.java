package com.ivan.doctorservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DoctorResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String status;
}
