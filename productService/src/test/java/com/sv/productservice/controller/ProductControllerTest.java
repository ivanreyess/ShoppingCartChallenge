package com.sv.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.productservice.dto.ProductDTO;
import com.sv.productservice.dto.RatingDTO;
import com.sv.productservice.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productService;

    ProductDTO product1, product2;

    RatingDTO rating1, rating2;

    @BeforeEach
    void setUp() {
        rating1 = RatingDTO.builder()
                .rate(9.4)
                .count(154)
                .build();

        rating2 = RatingDTO.builder()
                .rate(9.4)
                .count(154)
                .build();

        product1 = ProductDTO.builder()
                .id(1)
                .title("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops")
                .price(109.95d)
                .description("Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday")
                .category("men's clothing")
                .image("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg")
                .rating(rating1)
                .build();

        product2 = ProductDTO.builder()
                .id(2)
                .title("Mens Casual Premium Slim Fit T-Shirts")
                .price(22.3d)
                .description("great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.")
                .category("men's clothing")
                .image("https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg")
                .rating(rating2)
                .build();
    }

    @Test
    void getAllProducts() throws Exception {

        given(productService.getAll()).willReturn(List.of(product1, product2));

        ResultActions perform = mockMvc.perform(get("/api/products"));

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

    }

    @Test
    void getById() throws Exception {
        given(productService.getProductById(1)).willReturn(Optional.ofNullable(product1));

        ResultActions perform = mockMvc.perform(get("/api/products/" + 1));
        perform.andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getByIdNotFound() throws Exception {
        given(productService.getProductById(5)).willReturn(Optional.empty());

        ResultActions perform = mockMvc.perform(get("/api/products/" + 5));
        perform.andDo(print())
                .andExpect(status().isNotFound());

    }
}