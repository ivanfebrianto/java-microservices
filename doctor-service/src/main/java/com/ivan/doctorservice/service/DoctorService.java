package com.ivan.doctorservice.service;

import com.ivan.doctorservice.dto.DoctorResponseDTO;
import com.ivan.doctorservice.entity.Doctor;
import com.ivan.doctorservice.mapper.DoctorMapper;
import com.ivan.doctorservice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorResponseDTO> getDoctors() {
       List<Doctor> doctors = doctorRepository.findAll();
       return doctors.stream().map(DoctorMapper::toDoctorResponseDTO).toList();
    }


}
