package com.foodsafety.mapper;

import com.foodsafety.dto.TransactionDTO;
import com.foodsafety.model.Transaction;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransactionMapper extends GenericMapper <TransactionDTO, Transaction> {
}
