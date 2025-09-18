package com.ivan.patientservice.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;


@Service
public class BillingServiceGrpcClient  {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;
    private final ManagedChannel channel;


    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ){
        log.info("Initializing BillingServiceGrpcClient at {} : {}", serverAddress, serverPort);

        this.channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();
        this.billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(
            String patientId,
            String name,
            String email,
            String phoneNumber,
            String address
    ){
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setAddress(address)
                .build();
        BillingResponse response =  billingServiceBlockingStub.createBillingAccount(request);
        log.info("BillingServiceGrpcClient created response : {}", response);
        return response;
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down BillingServiceGrpcClient");
        channel.shutdown();
    }
}
