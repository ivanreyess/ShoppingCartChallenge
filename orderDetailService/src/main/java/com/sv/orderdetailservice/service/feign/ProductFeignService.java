package com.sv.orderdetailservice.service.feign;

import com.sv.orderdetailservice.client.ProductClientRest;
import com.sv.orderdetailservice.domain.dto.ProductDTO;
import com.sv.orderdetailservice.service.ProductService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductFeignService implements ProductService {

    private final ProductClientRest productClient;

    public ProductFeignService(ProductClientRest productClient) {
        this.productClient = productClient;
    }


    @Override
    public List<ProductDTO> findAll() {
        return productClient.findAll();
    }

    @Override
    public Optional<ProductDTO> getById(Integer id) {
        try {
         return Optional.of(productClient.getById(id));
        } catch (FeignException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
}
