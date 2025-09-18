package com.ivan.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.ivan.billingservice.entity.BillingAccountInfo;
import com.ivan.billingservice.repository.BillingAccountInfoRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(BillingGrpcService.class);
    private final BillingAccountInfoRepository billingAccountInfoRepository;

    public BillingGrpcService(BillingAccountInfoRepository billingAccountInfoRepository) {
        this.billingAccountInfoRepository = billingAccountInfoRepository;
    }

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver) {
        logger.info("📩 Received createBillingAccount request: {}", billingRequest);

        try {
            // 1️⃣ Validation
            if (billingRequest.getPatientId().isEmpty()
                    || billingRequest.getName().isEmpty()
                    || billingRequest.getEmail().isEmpty()
                    || billingRequest.getPhoneNumber().isEmpty()
                    || billingRequest.getAddress().isEmpty()) {

                logger.warn("⚠️ Validation failed: Missing required fields in request: {}", billingRequest);

                BillingResponse response = BillingResponse.newBuilder()
                        .setAccountId("")
                        .setPatientId(billingRequest.getPatientId())
                        .setStatus("FAILED: Missing required fields")
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            // 2️⃣ Generate Account ID
            String accountId = "ACC-" + UUID.randomUUID();
            logger.debug("Generated new accountId: {}", accountId);

            // 3️⃣ Save Data To DB
            BillingAccountInfo billingAccountInfo = new BillingAccountInfo();
            billingAccountInfo.setAccountId(accountId);
            billingAccountInfo.setPatientId(billingRequest.getPatientId());
            billingAccountInfo.setStatus(com.ivan.billingservice.enums.Status.ACTIVE);
            billingAccountInfo.setCreatedAt(LocalDateTime.now());
            billingAccountInfo.setExpiredDate(LocalDateTime.now().plusYears(1));

            billingAccountInfoRepository.save(billingAccountInfo);

            logger.info("✅ Account created successfully for patientId= {} with accountId= {} and status Account= {} ",
                    billingRequest.getPatientId(), accountId,billingAccountInfo.getStatus());

            // 4️⃣ Build Success Response
            BillingResponse response = BillingResponse.newBuilder()
                    .setAccountId(accountId)
                    .setPatientId(billingRequest.getPatientId())
                    .setStatus("ACTIVE")
                    .build();

            logger.debug("➡️ Sending response: {}", response);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception ex) {
            logger.error("❌ Unexpected error while processing createBillingAccount: {}", ex.getMessage(), ex);
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Server error: " + ex.getMessage())
                            .asRuntimeException()
            );
        }
    }

}
