package com.sv.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderDTO(Long id, String name, String lastName, Double total, Double discount, String city, String country, String email, String shippingAddress, long createdDate, long modifiedDate) {
}
