package com.foodsafety.mapper;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.dto.EventDTO;
import com.foodsafety.model.Category;
import com.foodsafety.model.Event;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper extends GenericMapper<EventDTO, Event>{
//    EventDTO toDTO(Event event);
//    Event toEntity(EventDTO eventDTO);
}
