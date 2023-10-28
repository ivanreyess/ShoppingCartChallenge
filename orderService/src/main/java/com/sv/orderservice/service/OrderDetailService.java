package com.sv.orderservice.service;

import com.sv.orderservice.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDTO> findAll();

    Double getTotal();
}
