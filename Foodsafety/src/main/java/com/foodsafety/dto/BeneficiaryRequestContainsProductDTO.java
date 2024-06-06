package com.foodsafety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodsafety.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class BeneficiaryRequestContainsProductDTO {

    private Long id;
    private int quantity;
    private String comment;
    private String size;

    @JsonIgnoreProperties

    private Long id_product;

    @JsonIgnoreProperties
    private Long id_benfRequest;
}
