package com.zestindia.productmanagement.controller;

import com.zestindia.productmanagement.dto.ItemResponse;
import com.zestindia.productmanagement.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{productId}/items")
    public ResponseEntity<List<ItemResponse>> getItems(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                itemService.getItemsByProductId(productId)
        );
    }
}