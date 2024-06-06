package com.foodsafety.mapper;


import com.foodsafety.dto.FundsDTO;
import com.foodsafety.model.Funds;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FundsMapper  extends GenericMapper <FundsDTO, Funds> {

}
