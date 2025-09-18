package com.ivan.patientservice.mapper;

import com.ivan.patientservice.dto.PatientRequestDTO;
import com.ivan.patientservice.dto.PatientResponseDTO;
import com.ivan.patientservice.entity.Patient;
import com.ivan.patientservice.enums.Gender;

import java.time.LocalDate;


public class PatientMapper {
    public static PatientResponseDTO toPatientResponseDTO(Patient patient){
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setFirstName(patient.getFirstName());
        patientResponseDTO.setLastName(patient.getLastName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setPhoneNumber(patient.getPhoneNumber());
        patientResponseDTO.setGender(patient.getGender().toString());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDTO;

    }

    public static Patient toEntity(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setFirstName(patientRequestDTO.getFirstName());
        patient.setLastName(patientRequestDTO.getLastName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setPhoneNumber(patientRequestDTO.getPhoneNumber());
        patient.setGender(Gender.valueOf(patientRequestDTO.getGender().toUpperCase()));
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setRegisteredDate(LocalDate.now());
        return patient;
    }
}
