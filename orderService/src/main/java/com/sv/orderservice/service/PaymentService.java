package com.sv.orderservice.service;

import com.sv.orderservice.domain.dto.OrderDTO;
import com.sv.orderservice.domain.dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO doPayment(Long orderId);

    Double calculateTotal(OrderDTO orderDTO);
}
