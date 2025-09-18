package com.ivan.patientservice.dto;

import com.ivan.patientservice.enums.Gender;
import com.ivan.patientservice.validation.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDTO {

    @NotBlank(message = "first name is required")
    @Size(min = 3, max = 50,message = "Name cannot exceed 50 Characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 3, max = 50,message = "Name cannot exceed 50 Characters")
    private String lastName;

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid!")
    private String email;

    @NotBlank(message = "address is required")
    @Size(min = 5, max = 255,message = "Name cannot exceed 255 Characters")
    private String address;

    @NotNull(message = "date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    @ValidEnum(enumClass = Gender.class, message = "Gender must be one of: MALE, FEMALE, UNDISCLOSED")
    private String gender;

}
