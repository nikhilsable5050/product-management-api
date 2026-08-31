package com.zestindia.productmanagement.service;

import com.zestindia.productmanagement.dto.ItemRequest;
import com.zestindia.productmanagement.dto.ItemResponse;
import com.zestindia.productmanagement.entity.Item;
import com.zestindia.productmanagement.entity.Product;
import com.zestindia.productmanagement.exception.ProductNotFoundException;
import com.zestindia.productmanagement.repository.ItemRepository;
import com.zestindia.productmanagement.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    public List<ItemResponse> getItemsByProductId(Long productId) {

        return itemRepository.findByProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ItemResponse mapToResponse(Item item) {

        return ItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .build();
    }

    public ItemResponse createItem(Long productId, ItemRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        Item item = new Item();

        item.setProduct(product);
        item.setQuantity(request.getQuantity());

        Item savedItem = itemRepository.save(item);

        return mapToResponse(savedItem);
    }
}