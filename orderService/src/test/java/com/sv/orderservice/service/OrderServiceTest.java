package com.sv.orderservice.service;

import com.sv.orderservice.domain.Order;
import com.sv.orderservice.domain.dto.OrderDTO;
import com.sv.orderservice.repository.OrderRepository;
import com.sv.orderservice.service.feign.OrderDetailFeignService;
import com.sv.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.sv.orderservice.config.AppConstants.*;
import static com.sv.orderservice.domain.Order.toDto;
import static com.sv.orderservice.domain.Order.toEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderDetailFeignService orderDetailService;

    Order order;
    OrderDTO orderDTO, orderDTO1;


    @BeforeEach
    void setUp() {

        order = new Order(1L, "Ivan", "Reyes", 23d, 5d,
                "San Salvador", "El Salvador", "ivan@mail.com", "Col. Costa Rica, San Salvador",
                1L, 1L);
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

        orderDTO1 = OrderDTO.builder()
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
    void save() {
        Order orderSaved = Order.builder()
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

        given(orderRepository.save(any(Order.class))).willReturn(orderSaved);
        given(orderDetailService.getTotal()).willReturn(23d);
        OrderDTO result = orderService.save(toDto(orderSaved));
        assertNotNull(result);
        assertEquals(23, result.total());
        assertEquals("ivan@mail.com", result.email());
        assertEquals("El Salvador", result.country());

    }

    @Test
    void update() {
        Order orderSaved = Order.builder()
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

        given(orderRepository.save(any(Order.class))).willReturn(orderSaved);
        given(orderDetailService.getTotal()).willReturn(23d);
        OrderDTO result = orderService.update(toDto(orderSaved));
        assertNotNull(result);
        assertEquals(23, result.total());
        assertEquals("ivan@mail.com", result.email());
        assertEquals("El Salvador", result.country());

    }

    @Test
    void partialUpdate() {
        Order order1 = toEntity(orderDTO);
        order1.setCity("Zaragoza");
        given(orderRepository.findById(any(Long.class))).willReturn(Optional.of(order1));
        given(orderRepository.save(any(Order.class))).willReturn(order1);
        Optional<OrderDTO> result = orderService.partialUpdate(toDto(order1));
        assertFalse(result.isEmpty());
    }

    @Test
    void findAll() {
        List<Order> orders = Stream.of(orderDTO, orderDTO1).map(Order::toEntity).toList();
        Page<Order> orderPage = new PageImpl<>(orders);
        given(orderRepository.findAll(any(Pageable.class))).willReturn(orderPage);
        Page<OrderDTO> result;
        result = orderService.findAll(Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_SIZE),DEFAULT_SORT_BY, DEFAULT_SORT_DIRECTION);
        assertFalse(result.getContent().isEmpty());
        assertEquals(2, result.getContent().size());
    }

    @Test
    void findOne() {

        Order foundOrder = toEntity(orderDTO);
        given(orderRepository.findById(2L)).willReturn(Optional.of(foundOrder));
        Optional<OrderDTO> result = orderService.findOne(2L);
        assertFalse(result.isEmpty());
    }


    @Test
    void exists() {
        doNothing().when(orderRepository).deleteById(1L);
        assertDoesNotThrow(() -> orderService.delete(1L));
    }
}