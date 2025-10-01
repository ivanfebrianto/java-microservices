package com.ivan.appointmentservice.mapper;

import appointment.Appointment;
import com.ivan.appointmentservice.entity.AppointmentInfo;

public class AppointmentMapper {
    public static Appointment toGrpc(AppointmentInfo a) {
        return Appointment.newBuilder()
                .setAppointmentId(String.valueOf(a.getAppointmentId()))
                .setPatientId(a.getPatientId())
                .setDoctorId(a.getDoctorId())
                .setAppointmentDate(a.getAppointmentDate().toString())
                .setStatus(String.valueOf(a.getStatus()))
                .setReason(a.getReason())
                .build();
    }
}

