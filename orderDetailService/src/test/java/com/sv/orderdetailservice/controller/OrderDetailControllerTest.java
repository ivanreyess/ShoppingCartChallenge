package com.sv.orderdetailservice.controller;

import com.sv.orderdetailservice.domain.dto.OrderDetailDTO;
import com.sv.orderdetailservice.service.OrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderDetailControllerTest {

    private static final String ENTITY_API_URL = "/api/order-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Long DEFAULT_ORDER_ID = 1L;
    private static final Long UPDATED_ORDER_ID = 2L;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_PRODUCT_ID = 1;
    private static final Integer UPDATED_PRODUCT_ID = 2;

    @Autowired
    private MockMvc restOrderDetailMockMvc;

    @MockBean
    private OrderDetailService orderDetailService;

    OrderDetailDTO orderDetailDto1, orderDetailDto2;

    @BeforeEach
    void setUp() {

        orderDetailDto1 = OrderDetailDTO.builder()
                .id(1L)
                .orderId(DEFAULT_ORDER_ID)
                .productId(DEFAULT_PRODUCT_ID)
                .quantity(DEFAULT_QUANTITY)
                .price(DEFAULT_PRICE)
                .build();

        orderDetailDto2 =
                OrderDetailDTO.builder()
                        .id(2L)
                        .orderId(1L)
                        .productId(1)
                        .quantity(40)
                        .price(25.50d)
                        .build();
    }

    @Test
    @DisplayName("POST /api/order-details")
    void createOrderDetail() throws Exception {
        orderDetailDto1 = OrderDetailDTO.builder()
                .orderId(DEFAULT_ORDER_ID)
                .productId(DEFAULT_PRODUCT_ID)
                .quantity(DEFAULT_QUANTITY)
                .price(DEFAULT_PRICE)
                .build();

        OrderDetailDTO orderDetailDtoSaved = OrderDetailDTO.builder()
                .id(1L)
                .orderId(DEFAULT_ORDER_ID)
                .productId(DEFAULT_PRODUCT_ID)
                .quantity(DEFAULT_QUANTITY)
                .price(DEFAULT_PRICE)
                .build();

        given(orderDetailService.save(any())).willReturn(orderDetailDtoSaved);

        restOrderDetailMockMvc
                .perform(
                        post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetailDto1))
                )
                .andExpect(status().isCreated());

        assertThat(orderDetailDto1.orderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(orderDetailDto1.quantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(orderDetailDto1.price()).isEqualTo(DEFAULT_PRICE);
        assertThat(orderDetailDto1.productId()).isEqualTo(DEFAULT_PRODUCT_ID);
    }

    @Test
    @DisplayName("POST /api/order-details Bad Request")
    void createOrderDetailWithExistingId() throws Exception {
        given(orderDetailService.save(any())).willReturn(orderDetailDto1);
        restOrderDetailMockMvc
                .perform(
                        post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetailDto1))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT " + ENTITY_API_URL_ID  + " OK")
    void updateOrderDetail() throws Exception {
        given(orderDetailService.exists(1L)).willReturn(true);

        given(orderDetailService.update(orderDetailDto1)).willReturn(orderDetailDto1);

        restOrderDetailMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, orderDetailDto1.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(orderDetailDto1))
                )
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("PATCH " + ENTITY_API_URL_ID  + " OK")
    void partialUpdateOrderDetail() throws Exception{
        given(orderDetailService.exists(2L)).willReturn(true);

        given(orderDetailService.partialUpdate(orderDetailDto2)).willReturn(Optional.ofNullable(orderDetailDto2));

        restOrderDetailMockMvc
                .perform(
                        patch(ENTITY_API_URL_ID, orderDetailDto2.id())
                                .contentType("application/merge-patch+json")
                                .content(TestUtil.convertObjectToJsonBytes(orderDetailDto2))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL_ID  + " NOT FOUND ")
    void getNonExistingOrderDetail() throws Exception {
        restOrderDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL_ID  + " OK")
    void getAllOrderDetails() throws Exception {
        List<OrderDetailDTO> orderDetails = Stream.of(orderDetailDto1).toList();
        Page<OrderDetailDTO> orderDetailPage = new PageImpl<>(orderDetails);
        given(orderDetailService.findAll(anyInt(), anyInt(), anyString(), anyString())).willReturn(orderDetailPage);

        restOrderDetailMockMvc
                .perform(get(ENTITY_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetailDto1.id().intValue())))
                .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.intValue())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
                .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)));

    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL_ID  + " OK ")
    void getOrderDetail() throws Exception {
        given(orderDetailService.findOne(1L)).willReturn(Optional.ofNullable(orderDetailDto1));
        restOrderDetailMockMvc
                .perform(get(ENTITY_API_URL_ID, orderDetailDto1.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(orderDetailDto1.id().intValue()))
                .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.intValue()))
                .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
                .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
                .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID));

    }

    @Test
    @DisplayName("DELETE " + ENTITY_API_URL_ID  + " no content ")
    void deleteOrderDetail() throws Exception {
        doNothing().when(orderDetailService).delete(1L);
        restOrderDetailMockMvc.perform(delete(ENTITY_API_URL_ID, 1L))
                .andExpect(status().isNoContent());
    }
}