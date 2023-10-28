package com.sv.orderdetailservice.service;

import com.sv.orderdetailservice.domain.OrderDetail;
import com.sv.orderdetailservice.domain.dto.OrderDetailDTO;
import com.sv.orderdetailservice.domain.dto.ProductDTO;
import com.sv.orderdetailservice.repository.OrderDetailRepository;
import com.sv.orderdetailservice.service.feign.ProductFeignService;
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

import static com.sv.orderdetailservice.config.AppConstants.*;
import static com.sv.orderdetailservice.domain.OrderDetail.toDto;
import static com.sv.orderdetailservice.domain.OrderDetail.toEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {

    @InjectMocks
    OrderDetailServiceImpl orderDetailService;

    @Mock
    OrderDetailRepository orderDetailRepository;

    @Mock
    ProductFeignService productService;


    OrderDetailDTO orderDetailDto1, orderDetailDto2;
    ProductDTO productDTO1, productDTO2;

    @BeforeEach
    void setUp() {

        orderDetailDto1 = OrderDetailDTO.builder()
                .id(1L)
                .orderId(1L)
                .productId(1)
                .quantity(10)
                .price(25.50d)
                .build();

        orderDetailDto2 =
                OrderDetailDTO.builder()
                        .id(2L)
                        .orderId(1L)
                        .productId(1)
                        .quantity(40)
                        .price(25.50d)
                        .build();

        productDTO1 = ProductDTO.builder()
                .id(1)
                .title("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops")
                .price(109.95d)
                .description("Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday")
                .category("men's clothing")
                .image("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg")
                .build();

        productDTO2 = ProductDTO.builder()
                .id(2)
                .title("Mens Casual Premium Slim Fit T-Shirts")
                .price(22.3d)
                .description("great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.")
                .category("men's clothing")
                .image("https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg")
                .build();
    }

    @Test
    void save() {
        ProductDTO productDTO = ProductDTO
                .builder()
                .id(1)
                .price(25.50d)
                .build();
        OrderDetail savedOrderDetail = toEntity(orderDetailDto1);
        given(orderDetailRepository.save(any(OrderDetail.class))).willReturn(savedOrderDetail);
        given(productService.getById(any())).willReturn(Optional.ofNullable(productDTO));
        OrderDetailDTO result = orderDetailService.save(toDto(savedOrderDetail));
        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals(25.50d, result.price());
    }

    @Test
    void update() {
        ProductDTO productDTO = ProductDTO
                .builder()
                .id(1)
                .price(25.50d)
                .build();
        OrderDetail savedOrderDetail = toEntity(orderDetailDto1);
        savedOrderDetail.setPrice(100.00d);
        given(orderDetailRepository.save(any(OrderDetail.class))).willReturn(savedOrderDetail);
        given(productService.getById(any())).willReturn(Optional.ofNullable(productDTO));
        OrderDetailDTO result = orderDetailService.update(toDto(savedOrderDetail));
        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals(100.00d, result.price());
    }

    @Test
    void partialUpdate() {
        OrderDetail savedOrderDetail = toEntity(orderDetailDto1);
        savedOrderDetail.setPrice(100.00d);
        given(orderDetailRepository.findById(any(Long.class))).willReturn(Optional.of(savedOrderDetail));
        given(orderDetailRepository.save(any(OrderDetail.class))).willReturn(savedOrderDetail);
        Optional<OrderDetailDTO> result = orderDetailService.partialUpdate(toDto(savedOrderDetail));
        assertFalse(result.isEmpty());
    }

    @Test
    void partialUpdateFailed() {
        OrderDetail savedOrderDetail = toEntity(orderDetailDto1);
        given(orderDetailRepository.findById(any(Long.class))).willReturn(Optional.empty());
        Optional<OrderDetailDTO> result = orderDetailService.partialUpdate(toDto(savedOrderDetail));
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll() {
        List<OrderDetail> orderDetails = Stream.of(orderDetailDto1, orderDetailDto2).map(OrderDetail::toEntity).toList();
        Page<OrderDetail> orderDetailPage = new PageImpl<>(orderDetails);
        given(orderDetailRepository.findAll(any(Pageable.class))).willReturn(orderDetailPage);
        Page<OrderDetailDTO> result = orderDetailService.findAll(Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_SIZE),DEFAULT_SORT_BY, DEFAULT_SORT_DIRECTION);
        assertTrue(result.getContent().size() > 0);
        assertEquals(2, result.getContent().size());

    }

    @Test
    void findOne() {
        OrderDetail foundOrderDetail = toEntity(orderDetailDto2);
        given(orderDetailRepository.findById(2L)).willReturn(Optional.of(foundOrderDetail));
        Optional<OrderDetailDTO> result = orderDetailService.findOne(2L);
        assertFalse(result.isEmpty());
    }

    @Test
    void findOneFailed() {
        given(orderDetailRepository.findById(2L)).willReturn(Optional.empty());
        Optional<OrderDetailDTO> result = orderDetailService.findOne(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void delete() {
        doNothing().when(orderDetailRepository).deleteById(1L);
        assertDoesNotThrow(() -> orderDetailService.delete(1L));
    }
}