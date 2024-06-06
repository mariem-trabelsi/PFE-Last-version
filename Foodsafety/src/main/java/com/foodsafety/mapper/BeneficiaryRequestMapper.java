package com.foodsafety.mapper;

import com.foodsafety.dto.BeneficiaryRequestDTO;
import com.foodsafety.model.BeneficiaryRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BeneficiaryRequestMapper extends GenericMapper <BeneficiaryRequestDTO, BeneficiaryRequest> {
}
