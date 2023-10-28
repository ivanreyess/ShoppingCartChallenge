package com.sv.orderservice.client;

import com.sv.orderservice.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "order-detail-service")
public interface OrderDetailClientRest {

    @GetMapping("/api/order-details")
    List<OrderDetailDTO> findAll();
}
