package com.zestindia.productmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {

    @Schema(
            description = "Unique ID of the item",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "ID of the product this item belongs to",
            example = "6"
    )
    private Long productId;

    @Schema(
            description = "Quantity available for the item",
            example = "10"
    )
    private Integer quantity;
}