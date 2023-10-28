package com.sv.orderservice.service;

import com.sv.orderservice.dto.OrderDTO;
import com.sv.orderservice.dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO doPayment(Long orderId);

    Double calculateTotal(OrderDTO orderDTO);
}
