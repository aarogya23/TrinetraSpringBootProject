package com.trinetra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trinetra.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
