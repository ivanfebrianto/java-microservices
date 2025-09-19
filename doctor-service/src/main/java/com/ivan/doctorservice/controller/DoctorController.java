package com.ivan.doctorservice.controller;

import com.ivan.doctorservice.api.ApiResponse;
import com.ivan.doctorservice.api.Meta;
import com.ivan.doctorservice.dto.DoctorResponseDTO;
import com.ivan.doctorservice.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
