package com.ivan.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
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
                        .setStatus("FAILED: Missing required fields")
                        .build();

                logger.info("➡️ Sending response: {}", response);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            // 2️⃣ Simulate business logic
            String accountId = "ACC-" + UUID.randomUUID();
            logger.debug("Generated new accountId: {}", accountId);

            BillingResponse response = BillingResponse.newBuilder()
                    .setAccountId(accountId)
                    .setStatus("SUCCESS")
                    .build();

            logger.info("✅ Account created successfully for patientId={} with accountId={}",
                    billingRequest.getPatientId(), accountId);

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
