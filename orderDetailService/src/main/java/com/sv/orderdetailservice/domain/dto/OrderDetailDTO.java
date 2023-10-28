package com.sv.orderdetailservice.domain.dto;

import lombok.Builder;

@Builder
public record OrderDetailDTO(Long id, Long orderId, Integer quantity, Double price, Integer productId, long createdDate, long modifiedDate) {
}
