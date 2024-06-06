package com.foodsafety.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.foodsafety.dto.TemporaryExpenseDTO;
import com.foodsafety.model.TemporaryExpense;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TemporaryExpenseMapper extends GenericMapper<TemporaryExpenseDTO, TemporaryExpense>{

}
