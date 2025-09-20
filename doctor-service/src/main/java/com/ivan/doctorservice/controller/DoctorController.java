package com.ivan.doctorservice.controller;

import com.ivan.doctorservice.api.ApiResponse;
import com.ivan.doctorservice.api.Meta;
import com.ivan.doctorservice.dto.DoctorRequestDTO;
import com.ivan.doctorservice.dto.DoctorResponseDTO;
import com.ivan.doctorservice.service.DoctorService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-service/v1/")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("get-doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getDoctors(){
        List<DoctorResponseDTO> doctors = doctorService.getDoctors();

        Meta meta = new Meta(1, doctors.size(), 1, doctors.size());

        ApiResponse<List<DoctorResponseDTO>> response = new ApiResponse<>(200, true, doctors, meta);
        return ResponseEntity.ok(response);
    }

    @PostMapping("create-doctor")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> createDoctor(@Valid @RequestBody DoctorRequestDTO doctorRequestDTO){
        DoctorResponseDTO doctorResponseDTO = doctorService.createDoctor(doctorRequestDTO);
        ApiResponse<DoctorResponseDTO> response = new ApiResponse<>(201, true, doctorResponseDTO);
        return ResponseEntity
                .created(URI.create("/api/doctor-service/v1/create-doctor/" + doctorResponseDTO.getId()))
                .body(response);
    }
}
