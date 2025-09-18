package com.ivan.billingservice.repository;

import com.ivan.billingservice.entity.BillingAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillingAccountInfoRepository extends JpaRepository<BillingAccountInfo, UUID> {
    Optional<BillingAccountInfo> findByAccountId(String accountId);
}
