package com.sv.productservice.service;

import com.sv.productservice.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> getAll();

    Optional<ProductDTO> getProductById(Integer id) throws InterruptedException;



}
