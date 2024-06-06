package com.foodsafety.mapper;


import com.foodsafety.dto.ProductDTO;
import com.foodsafety.model.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper  extends GenericMapper <ProductDTO, Product> {
}
