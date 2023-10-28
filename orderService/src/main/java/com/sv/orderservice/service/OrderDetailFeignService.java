package com.sv.orderservice.service;

import com.google.common.util.concurrent.AtomicDouble;
import com.sv.orderservice.client.OrderDetailClientRest;
import com.sv.orderservice.dto.OrderDetailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailFeignService implements OrderDetailService{

    private final OrderDetailClientRest orderDetailClientRest;

    public OrderDetailFeignService(OrderDetailClientRest orderDetailClientRest) {
        this.orderDetailClientRest = orderDetailClientRest;
    }

    @Override
    public List<OrderDetailDTO> findAll() {
        return orderDetailClientRest.findAll();
    }

    @Override
    public Double getTotal() {
        AtomicDouble total = new AtomicDouble(0);
        findAll().forEach(od -> total.addAndGet(od.price() * od.quantity()));
        return total.get();
    }
}
