package com.sv.orderservice.client;

import com.sv.orderservice.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClientRest {
    @PostMapping("/api/payments")
    PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO);
}