package com.zestindia.productmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.zestindia.productmanagement.dto.ItemRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.zestindia.productmanagement.dto.ItemResponse;
import com.zestindia.productmanagement.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Items",
        description = "APIs for managing product items"
)
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(
            summary = "Get items by product",
            description = "Returns all items belonging to a specific product"
    )
    @GetMapping("/{productId}/items")
    public ResponseEntity<List<ItemResponse>> getItems(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                itemService.getItemsByProductId(productId)
        );
    }

    @Operation(
            summary = "Create item",
            description = "Creates a new item for a specific product"
    )
    @PostMapping("/{productId}/items")
    public ResponseEntity<ItemResponse> createItem(
            @PathVariable Long productId,
            @Valid @RequestBody ItemRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.createItem(productId, request));
    }
}