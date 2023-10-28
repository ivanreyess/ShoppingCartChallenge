package com.sv.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderDetailDTO(Long id, Long orderId, Integer quantity, Double price, Integer productId) {
}
