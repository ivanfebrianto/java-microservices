package com.ivan.billingservice.entity;

import com.ivan.billingservice.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "BILLING_ACCOUNT_INFO")
public class BillingAccountInfo {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "account_id", nullable = false, unique = true)
    private String accountId;

    @NotNull
    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

}
