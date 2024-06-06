package com.foodsafety.mapper;


import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.model.SubCategory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SubCategoryMapper extends GenericMapper<SubCategoryDTO, SubCategory> {
}
