package com.sv.paymentservice.dto;

import com.sv.paymentservice.domain.PaymentStatus;
import lombok.Builder;

@Builder
public record PaymentDTO(Long id, Long orderId, PaymentStatus status, Double total, long createdDate, long modifiedDate) {
}
