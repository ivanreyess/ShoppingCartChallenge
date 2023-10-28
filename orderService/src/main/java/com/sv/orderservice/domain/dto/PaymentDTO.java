package com.sv.orderservice.domain.dto;

import com.sv.orderservice.domain.PaymentStatus;
import lombok.Builder;

@Builder
public record PaymentDTO(Long id, Long orderId, PaymentStatus status, Double total, Long createdDate, Long modifiedDate) {
}
