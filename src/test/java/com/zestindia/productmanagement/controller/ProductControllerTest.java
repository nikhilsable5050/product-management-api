package com.zestindia.productmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zestindia.productmanagement.dto.ProductRequest;
import com.zestindia.productmanagement.dto.ProductResponse;
import com.zestindia.productmanagement.exception.ProductNotFoundException;
import com.zestindia.productmanagement.security.CustomUserDetailsService;
import com.zestindia.productmanagement.security.JwtService;
import com.zestindia.productmanagement.service.ProductService;

import org.junit.jupiter.api.Test;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;


    @Test
    void createProduct_shouldReturn201() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setProductName("Laptop");

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .productName("Laptop")
                .createdBy("SYSTEM")
                .createdOn(LocalDateTime.now())
                .build();

        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Laptop"));
    }


    @Test
    void getProductById_shouldReturn200() throws Exception {

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .productName("Laptop")
                .createdBy("SYSTEM")
                .createdOn(LocalDateTime.now())
                .build();

        when(productService.getProductById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Laptop"));
    }


    @Test
    void getProductById_shouldReturn404WhenNotFound()
            throws Exception {

        when(productService.getProductById(99L))
                .thenThrow(
                        new ProductNotFoundException("Product not found")
                );

        mockMvc.perform(
                        get("/api/v1/products/99")
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void getAllProducts_shouldReturn200() throws Exception {

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .productName("Laptop")
                .createdBy("SYSTEM")
                .createdOn(LocalDateTime.now())
                .build();

        PageImpl<ProductResponse> page =
                new PageImpl<>(
                        List.of(response),
                        PageRequest.of(0, 10),
                        1
                );

        when(productService.getAllProducts(any()))
                .thenReturn(page);

        mockMvc.perform(
                        get("/api/v1/products")
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(
                        jsonPath("$.content[0].productName")
                                .value("Laptop")
                );
    }


    @Test
    void updateProduct_shouldReturn200() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setProductName("Updated Laptop");

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .productName("Updated Laptop")
                .modifiedBy("SYSTEM")
                .modifiedOn(LocalDateTime.now())
                .build();

        when(
                productService.updateProduct(
                        eq(1L),
                        any(ProductRequest.class)
                )
        ).thenReturn(response);

        mockMvc.perform(
                        put("/api/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.productName")
                                .value("Updated Laptop")
                );
    }


    @Test
    void deleteProduct_shouldReturn204() throws Exception {

        doNothing()
                .when(productService)
                .deleteProduct(1L);

        mockMvc.perform(
                        delete("/api/v1/products/1")
                )
                .andExpect(status().isNoContent());

        verify(productService)
                .deleteProduct(1L);
    }
}