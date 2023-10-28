package com.sv.orderdetailservice.domain;

import com.sv.orderdetailservice.domain.dto.OrderDetailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class OrderDetailTest {

    OrderDetail orderDetail;
    OrderDetailDTO orderDetailDTO;

    @BeforeEach
    void setUp() {
        orderDetail = OrderDetail.builder()
                .id(1L)
                .orderId(1L)
                .quantity(2)
                .productId(1)
                .price(2d)
                .createdDate(1L)
                .modifiedDate(1L)
                .build();

        orderDetailDTO = OrderDetailDTO.builder()
                .orderId(1L)
                .id(1L)
                .productId(1)
                .price(2d)
                .quantity(2)
                .createdDate(1L)
                .modifiedDate(1L)
                .build();
    }

    @Test
    void toEntity() {
        OrderDetail orderDetail1 = OrderDetail.toEntity(orderDetailDTO);
        assertEquals(1, orderDetail1.getOrderId());
        assertEquals(1, orderDetail1.getProductId());
        assertEquals(2, orderDetail1.getQuantity());
        assertEquals(2, orderDetail1.getPrice());
    }

    @Test
    void toDto() {
        OrderDetailDTO orderDetailDTO1 = OrderDetail.toDto(orderDetail);
        assertEquals(1, orderDetailDTO1.orderId());
        assertEquals(1, orderDetailDTO1.productId());
        assertEquals(2, orderDetailDTO1.quantity());
        assertEquals(2, orderDetailDTO1.price());
    }
}