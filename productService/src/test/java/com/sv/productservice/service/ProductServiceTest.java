package com.sv.productservice.service;

import com.sv.productservice.dto.ProductDTO;
import com.sv.productservice.dto.RatingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private final String resourceUrl = "https://fakestoreapi.com/products";

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    private RestTemplate restTemplate;

    ProductDTO product1;

    @BeforeEach
    void setUp() {

        RatingDTO rating1 = RatingDTO.builder()
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
    }

    @Test
    void findAll() {

        given(restTemplate.getForObject(resourceUrl, ProductDTO[].class)).willReturn(new ProductDTO[]{product1});

        List<ProductDTO> products = productService.getAll();

        assertEquals(1, products.size());

    }

    @Test
    void getProductById() throws InterruptedException {
        given(restTemplate.getForObject(resourceUrl + "/{id}", ProductDTO.class, 1)).willReturn(product1);
        Optional<ProductDTO> product = productService.getProductById(1);
        assertFalse(product.isEmpty());
    }
}