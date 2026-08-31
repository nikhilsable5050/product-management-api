package com.zestindia.productmanagement.service;

import com.zestindia.productmanagement.dto.ItemRequest;
import com.zestindia.productmanagement.dto.ItemResponse;
import com.zestindia.productmanagement.entity.Item;
import com.zestindia.productmanagement.entity.Product;
import com.zestindia.productmanagement.exception.ProductNotFoundException;
import com.zestindia.productmanagement.repository.ItemRepository;
import com.zestindia.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void getItemsByProductId_shouldReturnItems() {

        Product product = new Product();
        product.setId(1L);

        Item item = new Item();
        item.setId(10L);
        item.setProduct(product);
        item.setQuantity(5);

        when(itemRepository.findByProductId(1L))
                .thenReturn(List.of(item));

        List<ItemResponse> response =
                itemService.getItemsByProductId(1L);

        assertEquals(1, response.size());
        assertEquals(10L, response.get(0).getId());
        assertEquals(1L, response.get(0).getProductId());
        assertEquals(5, response.get(0).getQuantity());

        verify(itemRepository).findByProductId(1L);
    }

    @Test
    void getItemsByProductId_shouldReturnEmptyList() {

        when(itemRepository.findByProductId(1L))
                .thenReturn(List.of());

        List<ItemResponse> response =
                itemService.getItemsByProductId(1L);

        assertTrue(response.isEmpty());

        verify(itemRepository).findByProductId(1L);
    }

    @Test
    void createItem_shouldCreateItem() {

        Product product = new Product();
        product.setId(1L);
        product.setProductName("Laptop");

        ItemRequest request = new ItemRequest();
        request.setQuantity(5);

        Item savedItem = new Item();
        savedItem.setId(10L);
        savedItem.setProduct(product);
        savedItem.setQuantity(5);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(itemRepository.save(any(Item.class)))
                .thenReturn(savedItem);

        ItemResponse response =
                itemService.createItem(1L, request);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals(1L, response.getProductId());
        assertEquals(5, response.getQuantity());

        verify(productRepository).findById(1L);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void createItem_shouldThrowExceptionWhenProductNotFound() {

        ItemRequest request = new ItemRequest();
        request.setQuantity(5);

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> itemService.createItem(99L, request)
        );

        verify(productRepository).findById(99L);
        verify(itemRepository, never()).save(any(Item.class));
    }
}