package com.payment.microservice.repository;

import java.util.List;

import com.payment.microservice.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    public List<Payment> findByOrderId(long orderId);
}
