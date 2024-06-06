package com.foodsafety.mapper;

import com.foodsafety.dto.CampaignRequestDTO;
import com.foodsafety.model.CampaignRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CampaignRequestMapper extends GenericMapper <CampaignRequestDTO, CampaignRequest> {
}
