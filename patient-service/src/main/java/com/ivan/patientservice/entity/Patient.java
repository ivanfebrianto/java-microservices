package com.ivan.patientservice.entity;

import com.ivan.patientservice.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "PATIENT_INFO")
public class Patient {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "ROWID_OBJECT")
    private UUID id;

    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private Gender gender;

    @NotNull
    @Email
    @Column(unique = true,name = "EMAIL")
    private String email;

    @NotNull
    @Column(name = "ADDRESS")
    private String address;

    @NotNull
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "REGISTERED_DATE")
    private LocalDate registeredDate;

}
