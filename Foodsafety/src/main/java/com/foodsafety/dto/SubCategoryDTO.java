package com.foodsafety.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder

public class SubCategoryDTO {

    private Long subCategoryId;

    private String subcategoryName;

    private String groupId;

    @JsonIgnoreProperties
    private Long category_id;
    
    public SubCategoryDTO(long id, String name, String groupId, long categoryId) {
        this.subCategoryId = id;
        this.subcategoryName = name;
        this.groupId = groupId;
        this.category_id = categoryId;
    }


	@JsonIgnoreProperties
    private List<ProductDTO> productDTOS;



	public List<ProductDTO> getProductDTOS() {
		return productDTOS;
	}

//	public void setProductDTOS(List<ProductDTO> fromEntitiesToDTOList) {
//		productDTOS = fromEntitiesToDTOList;
//	}

}
