package com.sv.orderservice.domain;

import com.sv.orderservice.domain.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    Order order;
    OrderDTO orderDTO;

    @BeforeEach
    void setUp() {

        order = new Order(1L, "Ivan", "Reyes", 23d, 5d,
                        "San Salvador", "El Salvador" ,"ivan@mail.com", "Col. Costa Rica, San Salvador",
                        1L, 1L );
        orderDTO = OrderDTO.builder()
                .id(1L)
                .name("Ivan")
                .lastName("Reyes")
                .total(23d)
                .discount(5d)
                .city("San Salvador")
                .country("El Salvador")
                .email("ivan@mail.com")
                .shippingAddress("Col. Costa Rica, San Salvador")
                .createdDate(1L)
                .modifiedDate(1L)
                .build();
    }

    @Test
    void toDto() {
        OrderDTO orderDTOTest =Order.toDto(order);
        assertEquals("Ivan", orderDTOTest.name());
        assertEquals("Reyes", orderDTOTest.lastName());
        assertEquals(23d, orderDTOTest.total());
        assertEquals("El Salvador", orderDTOTest.country());
        assertEquals(1L, orderDTOTest.createdDate());
    }

    @Test
    void toEntity() {
        Order orderTest = Order.toEntity(orderDTO);
        assertEquals("Ivan", orderTest.getName());
        assertEquals("Reyes", orderTest.getLastName());
        assertEquals(23d, orderTest.getTotal());
        assertEquals("El Salvador", orderTest.getCountry());
        assertEquals(1L, orderTest.getCreatedDate());
    }
}