package com.foodsafety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long productId;

    private String productName;

    private float unitPrice;

    private String unit;

    private String groupId;

    @JsonIgnoreProperties
    private Long subCategory_id;

    public ProductDTO(Long productId, String productName, float unitPrice, String unit, String groupId, Long subCategory_id) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.groupId = groupId;
        this.subCategory_id = subCategory_id;
    }
}
