package com.foodsafety.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodsafety.model.SubCategory;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;
    private String groupId;

    @JsonIgnoreProperties
    private List<SubCategoryDTO> subCategoryDTOS;

	public List<SubCategoryDTO> getSubCategoryDTOS() {
		return subCategoryDTOS;
	}

	
}
