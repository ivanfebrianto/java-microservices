package com.ivan.doctorservice.entity;


import com.ivan.doctorservice.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "DOCTOR_INFO")
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "ROWID_OBJECT")
    private UUID id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(name = "join_date")
    private LocalDate joinDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
