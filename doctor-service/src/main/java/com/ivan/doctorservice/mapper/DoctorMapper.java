package com.ivan.doctorservice.mapper;

import com.ivan.doctorservice.dto.DoctorRequestDTO;
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

    public static Doctor toEntity(DoctorRequestDTO doctorRequestDTO){
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorRequestDTO.getFirstName());
        doctor.setLastName(doctorRequestDTO.getLastName());
        doctor.setEmail(doctorRequestDTO.getEmail());
        doctor.setPhoneNumber(doctorRequestDTO.getPhoneNumber());
        doctor.setStatus(Status.ACTIVE);
        doctor.setJoinDate(LocalDate.now());
        return doctor;
    }
}
