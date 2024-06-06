package com.foodsafety.mapper;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.model.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper extends GenericMapper<CategoryDTO, Category>{

}
