package com.sv.orderdetailservice.domain.dto;

import lombok.Builder;

@Builder
public record ProductDTO(int id, String title, double price, String description, String category, String image) {
}
