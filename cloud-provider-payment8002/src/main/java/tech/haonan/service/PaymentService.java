package tech.haonan.service;

import org.apache.ibatis.annotations.Param;
import tech.haonan.entities.Payment;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
