package com.trinetra.repository;

import com.trinetra.model.PaymentData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentDataRepository extends JpaRepository<PaymentData, Long> {
    Optional<PaymentData> findByPhone(String phone);
    Optional<PaymentData> findByEmail(String email);
    Optional<PaymentData> findByKhaltiMobile(String khaltiMobile);
    Optional<PaymentData> findByEsewaId(String esewaId);
    Optional<PaymentData> findByImepayMobile(String imepayMobile);
}