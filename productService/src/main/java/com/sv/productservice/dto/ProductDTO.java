package com.sv.productservice.dto;

import lombok.Builder;

@Builder
public record ProductDTO(int id, String title, double price, String description, String category, String image, RatingDTO rating) {
}
