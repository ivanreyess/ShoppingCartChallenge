package com.sv.orderdetailservice.client;

import com.sv.orderdetailservice.domain.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClientRest {

    @GetMapping("/api/products")
    List<ProductDTO> findAll();

    @GetMapping("/api/products/{id}")
    ProductDTO getById(@PathVariable Integer id);
}
