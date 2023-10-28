package com.sv.orderservice.service.feign;

import com.google.common.util.concurrent.AtomicDouble;
import com.sv.orderservice.client.OrderDetailClientRest;
import com.sv.orderservice.domain.dto.OrderDetailDTO;
import com.sv.orderservice.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailFeignService implements OrderDetailService {

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
