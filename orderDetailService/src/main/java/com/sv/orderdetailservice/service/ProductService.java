package com.sv.orderdetailservice.service;

import com.sv.orderdetailservice.domain.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();

    Optional<ProductDTO> getById(Integer id);
}
