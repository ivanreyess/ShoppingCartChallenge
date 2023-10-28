package com.sv.orderservice.service;

import com.sv.orderservice.domain.dto.OrderDTO;
import com.sv.orderservice.repository.OrderRepository;
import com.sv.orderservice.service.feign.OrderDetailFeignService;
import com.sv.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderDetailFeignService orderDetailService;


    OrderDTO orderDTO;


    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void partialUpdate() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findOne() {
    }

    @Test
    void delete() {
    }

    @Test
    void exists() {
    }
}