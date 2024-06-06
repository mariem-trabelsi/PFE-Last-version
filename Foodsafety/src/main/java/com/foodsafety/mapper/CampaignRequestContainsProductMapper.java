package com.foodsafety.mapper;


import com.foodsafety.dto.CampaignRequestContainsProductDTO;
import com.foodsafety.model.CampaignRequestContainsProduct;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CampaignRequestContainsProductMapper extends GenericMapper <CampaignRequestContainsProductDTO, CampaignRequestContainsProduct>{
}
