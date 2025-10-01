package com.ivan.appointmentservice.entity;

import com.ivan.appointmentservice.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "appointment_info")
public class AppointmentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rowid_object")
    private UUID appointmentId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "doctor_id", nullable = false)
    private String doctorId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;  // e.g., PENDING, CONFIRMED, CANCELLED
}
