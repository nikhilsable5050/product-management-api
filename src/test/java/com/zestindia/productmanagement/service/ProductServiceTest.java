package com.zestindia.productmanagement.service;

import com.zestindia.productmanagement.dto.ProductRequest;
import com.zestindia.productmanagement.dto.ProductResponse;
import com.zestindia.productmanagement.entity.Product;
import com.zestindia.productmanagement.exception.ProductNotFoundException;
import com.zestindia.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldCreateProduct() {

        ProductRequest request = new ProductRequest();
        request.setProductName("Laptop");

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setProductName("Laptop");
        savedProduct.setCreatedBy("SYSTEM");
        savedProduct.setCreatedOn(LocalDateTime.now());

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Laptop", response.getProductName());
        assertEquals("SYSTEM", response.getCreatedBy());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getProductById_shouldReturnProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setProductName("Laptop");
        product.setCreatedBy("SYSTEM");
        product.setCreatedOn(LocalDateTime.now());

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Laptop", response.getProductName());

        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_shouldThrowExceptionWhenNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(99L)
        );
    }

    @Test
    void getAllProducts_shouldReturnProducts() {

        Product product = new Product();
        product.setId(1L);
        product.setProductName("Laptop");
        product.setCreatedBy("SYSTEM");
        product.setCreatedOn(LocalDateTime.now());

        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(any(PageRequest.class)))
                .thenReturn(page);

        Page<ProductResponse> response =
                productService.getAllProducts(PageRequest.of(0, 10));

        assertEquals(1, response.getTotalElements());
        assertEquals(
                "Laptop",
                response.getContent().get(0).getProductName()
        );
    }

    @Test
    void updateProduct_shouldUpdateProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setProductName("Old Laptop");
        product.setCreatedBy("SYSTEM");
        product.setCreatedOn(LocalDateTime.now());

        ProductRequest request = new ProductRequest();
        request.setProductName("New Laptop");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponse response =
                productService.updateProduct(1L, request);

        assertEquals("New Laptop", response.getProductName());
        assertEquals("SYSTEM", response.getModifiedBy());
        assertNotNull(response.getModifiedOn());

        verify(productRepository).save(product);
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setProductName("Laptop");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }
}