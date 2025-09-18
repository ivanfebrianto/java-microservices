package com.ivan.patientservice.service;

import com.ivan.patientservice.dto.PatientRequestDTO;
import com.ivan.patientservice.dto.PatientResponseDTO;
import com.ivan.patientservice.entity.Patient;
import com.ivan.patientservice.enums.Gender;
import com.ivan.patientservice.exception.DuplicateEmailException;
import com.ivan.patientservice.exception.PatientNotFoundException;
import com.ivan.patientservice.grpc.BillingServiceGrpcClient;
import com.ivan.patientservice.mapper.PatientMapper;
import com.ivan.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    @Autowired
    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toPatientResponseDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        // Check if email exists
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new DuplicateEmailException(patientRequestDTO.getEmail());
        }
        // Save patient
        try {
            Patient newPatient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
            billingServiceGrpcClient.createBillingAccount(
                    newPatient.getId().toString(),
                    newPatient.getFirstName() +" "+ newPatient.getLastName(),
                    newPatient.getEmail(),
                    newPatient.getPhoneNumber(),
                    newPatient.getAddress());
            return PatientMapper.toPatientResponseDTO(newPatient);
        } catch (DataIntegrityViolationException e) {
            // Handle race condition
            throw new DuplicateEmailException(patientRequestDTO.getEmail());
        }
    }


    public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO patientRequestDTO){
        // 1️⃣ Check Patient with such ID Exist or Not
         Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with id " + id));

        // 2️⃣ If yes, Check Again if the new Email already exist for another patient and have different ID
        Optional<Patient> existingPatientWithEmail = patientRepository.findByEmail(patientRequestDTO.getEmail());
        if (existingPatientWithEmail.isPresent() && !existingPatientWithEmail.get().getId().equals(id)) {
            throw new DataIntegrityViolationException("Duplicate email found");
        }

        // 3️⃣ Update patient fields
        patient.setFirstName(patientRequestDTO.getFirstName());
        patient.setLastName(patientRequestDTO.getLastName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setPhoneNumber(patientRequestDTO.getPhoneNumber());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setGender(Gender.valueOf(patientRequestDTO.getGender().toUpperCase()));

        return PatientMapper.toPatientResponseDTO(patientRepository.save(patient));
    }

    public void deletePatient(UUID id){
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with id " + id));
        patientRepository.delete(patient);
    }


}
