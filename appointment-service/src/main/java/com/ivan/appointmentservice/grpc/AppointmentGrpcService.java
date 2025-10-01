package com.ivan.appointmentservice.grpc;

import appointment.*;
import appointment.AppointmentServiceGrpc;
import com.ivan.appointmentservice.entity.AppointmentInfo;
import com.ivan.appointmentservice.mapper.AppointmentMapper;
import com.ivan.appointmentservice.repository.AppointmentInfoRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@GrpcService
@Slf4j
public class AppointmentGrpcService extends AppointmentServiceGrpc.AppointmentServiceImplBase {

    private final AppointmentInfoRepository appointmentInfoRepository;


    @Autowired
    public AppointmentGrpcService(AppointmentInfoRepository appointmentInfoRepository) {
        this.appointmentInfoRepository = appointmentInfoRepository;
    }

    @Override
    public void createAppointment(AppointmentRequest appointmentRequest, StreamObserver<AppointmentResponse> responseObserver) {
        try {
            log.info("📩 Received createAppointment request: {}", appointmentRequest);

            if (appointmentRequest.getPatientId().isEmpty() || appointmentRequest.getDoctorId().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Missing Required Fields!").asRuntimeException());
                return;
            }

            LocalDateTime appointmentDate;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                appointmentDate = LocalDateTime.parse(appointmentRequest.getAppointmentDate(), formatter);
            } catch (DateTimeParseException e) {
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Invalid date format. Expected ISO_LOCAL_DATE_TIME.").asRuntimeException());
                return;
            }

            AppointmentInfo appointmentInfo = new AppointmentInfo();
            appointmentInfo.setPatientId(appointmentRequest.getPatientId());
            appointmentInfo.setDoctorId(appointmentRequest.getDoctorId());
            appointmentInfo.setAppointmentDate(appointmentDate);
            appointmentInfo.setReason(appointmentRequest.getReason());
            appointmentInfo.setStatus(com.ivan.appointmentservice.enums.Status.CREATED);
            appointmentInfoRepository.save(appointmentInfo);

            AppointmentResponse appointmentResponse = AppointmentResponse.newBuilder()

                    .setPatientId(appointmentInfo.getPatientId())
                    .setDoctorId(appointmentInfo.getDoctorId())
                    .setStatus(appointmentInfo.getStatus().name())
                    .build();

            responseObserver.onNext(appointmentResponse);
            responseObserver.onCompleted();
            log.info("✅ Appointment created: {}", appointmentResponse);

        } catch (Exception e) {
            log.error("❌ Error creating appointment: {}", e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL.withDescription("Server error: " + e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getAppointmentsByPatient(GetAppointmentsByPatientRequest request, StreamObserver<AppointmentsListResponse> responseObserver) {
        List<AppointmentInfo> appointmentInfos = appointmentInfoRepository.findByPatientId(request.getPatientId());

        List<Appointment> grpcAppointments = appointmentInfos.stream()
                .map(AppointmentMapper::toGrpc)
                .collect(Collectors.toList());

        AppointmentsListResponse response = AppointmentsListResponse.newBuilder()
                .addAllAppointments(grpcAppointments)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAppointmentsByDoctor(GetAppointmentsByDoctorRequest request, StreamObserver<AppointmentsListResponse> responseObserver) {
        List<AppointmentInfo> appointmentInfos = appointmentInfoRepository.findByDoctorId(request.getDoctorId());

        List<Appointment> grpcAppointments = appointmentInfos.stream()
                .map(AppointmentMapper::toGrpc)
                .collect(Collectors.toList());

        AppointmentsListResponse response = AppointmentsListResponse.newBuilder()
                .addAllAppointments(grpcAppointments)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
