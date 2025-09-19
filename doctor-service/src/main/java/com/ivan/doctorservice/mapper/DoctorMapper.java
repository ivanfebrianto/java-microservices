package com.ivan.doctorservice.mapper;

import com.ivan.doctorservice.dto.DoctorResponseDTO;
import com.ivan.doctorservice.entity.Doctor;
import com.ivan.doctorservice.enums.Status;

import java.time.LocalDate;

public class DoctorMapper {

    public static DoctorResponseDTO toDoctorResponseDTO(Doctor doctor){
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setFirstName(doctor.getFirstName());
        doctorResponseDTO.setLastName(doctor.getLastName());
        doctorResponseDTO.setEmail(doctor.getEmail());
        doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDTO.setStatus(doctor.getStatus().toString());
        return doctorResponseDTO;
    }

    public static Doctor toEntity(DoctorResponseDTO doctorResponseDTO){
        Doctor doctor = new Doctor();
        doctor.setId(doctorResponseDTO.getId());
        doctor.setFirstName(doctorResponseDTO.getFirstName());
        doctor.setLastName(doctorResponseDTO.getLastName());
        doctor.setEmail(doctorResponseDTO.getEmail());
        doctor.setPhoneNumber(doctorResponseDTO.getPhoneNumber());
        doctor.setStatus(Status.valueOf(doctorResponseDTO.getStatus().toUpperCase()));
        doctor.setJoinDate(LocalDate.now());
        return doctor;
    }
}
