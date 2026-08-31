package com.zestindia.productmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponse {

    @Schema(
            description = "Unique ID of the product",
            example = "6"
    )
    private Long id;

    @Schema(
            description = "Name of the product",
            example = "Dell Inspiron 15 Laptop"
    )
    private String productName;

    @Schema(
            description = "Username of the user who created the product",
            example = "nikhil"
    )
    private String createdBy;

    @Schema(
            description = "Date and time when the product was created",
            example = "2026-08-31T14:30:00"
    )
    private LocalDateTime createdOn;

    @Schema(
            description = "Username of the user who last modified the product",
            example = "nikhil"
    )
    private String modifiedBy;

    @Schema(
            description = "Date and time when the product was last modified",
            example = "2026-08-31T15:45:00"
    )
    private LocalDateTime modifiedOn;
}