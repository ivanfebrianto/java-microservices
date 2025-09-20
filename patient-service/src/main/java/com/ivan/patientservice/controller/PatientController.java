package com.ivan.patientservice.controller;

import com.ivan.patientservice.api.ApiResponse;
import com.ivan.patientservice.api.Meta;
import com.ivan.patientservice.dto.PatientRequestDTO;
import com.ivan.patientservice.dto.PatientResponseDTO;
import com.ivan.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient-service/v1/")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("get-patients")
    @Operation(summary = "GET PATIENTS")
    public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();

        Meta meta = new Meta(1, patients.size(), 1, patients.size());

        ApiResponse<List<PatientResponseDTO>> response =
                new ApiResponse<>(200, true, patients, meta);

        return ResponseEntity.ok(response);
    }

    @GetMapping("get-patient/{id}")
    @Operation(summary = "GET PATIENT BY ID")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> getPatientById(@PathVariable UUID id){
        PatientResponseDTO patientResponseDTO = patientService.getPatientById(id);
        ApiResponse<PatientResponseDTO> response =
                new ApiResponse<>(200, true, patientResponseDTO, null);

        return ResponseEntity.ok(response);
    }


    @PostMapping("create-patient")
    @Operation(summary = "CREATE PATIENT")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(
            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO createdPatient = patientService.createPatient(patientRequestDTO);

        ApiResponse<PatientResponseDTO> response =
                new ApiResponse<>(201, true, createdPatient, null);

        // return 201 Created + Location header with new resource URI
        return ResponseEntity
                .created(URI.create("/api/patient-service/v1/create-patient/" + createdPatient.getId()))
                .body(response);
    }

    @PutMapping("update-patient/{id}")
    @Operation(summary = "UPDATE A PATIENT")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> updatePatient(@PathVariable UUID id,@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO updatePatient = patientService.updatePatient(id,patientRequestDTO);
        ApiResponse<PatientResponseDTO> response = new ApiResponse<>(200, true,updatePatient , null);
        // return 200 successfully updated
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("delete-patient/{id}")
    @Operation(summary = "DELETE A PATIENT")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        ApiResponse<PatientResponseDTO> response = new ApiResponse<>(200, true,null, null);
        return ResponseEntity.ok(response);
    }

}
