package com.zestindia.productmanagement.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {

    private Long id;
    private Long productId;
    private Integer quantity;
}