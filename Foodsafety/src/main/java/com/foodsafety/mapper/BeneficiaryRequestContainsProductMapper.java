package com.foodsafety.mapper;

import com.foodsafety.dto.BeneficiaryRequestContainsProductDTO;
import com.foodsafety.model.BeneficiaryRequestContainsProduct;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface BeneficiaryRequestContainsProductMapper extends GenericMapper<BeneficiaryRequestContainsProductDTO, BeneficiaryRequestContainsProduct> {
}
