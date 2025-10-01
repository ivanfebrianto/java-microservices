package com.ivan.appointmentservice.repository;

import com.ivan.appointmentservice.entity.AppointmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentInfoRepository extends JpaRepository<AppointmentInfo, UUID> {
    List<AppointmentInfo> findByPatientId(String patientId);

    List<AppointmentInfo> findByDoctorId(String doctorId);
}
