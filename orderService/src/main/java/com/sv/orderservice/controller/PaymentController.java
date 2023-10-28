package com.sv.orderservice.controller;

import com.sv.orderservice.dto.PaymentDTO;
import com.sv.orderservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<PaymentDTO> doPayment(@PathVariable Long id) throws URISyntaxException {
      log.info("Executing payment for order id: " + id);
      PaymentDTO result = paymentService.doPayment(id);
      if (result.id() == null) return ResponseEntity.badRequest().build();
      return ResponseEntity.created(new URI("/api/payments/" + result.id())).body(result);
    }

}
