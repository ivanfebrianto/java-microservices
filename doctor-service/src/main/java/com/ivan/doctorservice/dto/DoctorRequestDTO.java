package com.ivan.doctorservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorRequestDTO {

    @NotBlank(message = "first name is required!")
    @Size(min = 3, max = 50,message = "Name cannot exceed 50 Characters")
    private String firstName;

    @NotBlank(message = "first name is required!")
    @Size(min = 3, max = 50,message = "Name cannot exceed 50 Characters")
    private String lastName;

    @NotBlank(message = "email is required!")
    @Email(message = "Email should be valid!")
    private String email;

    @NotBlank(message = "phone number is required!")
    private String phoneNumber;

}
