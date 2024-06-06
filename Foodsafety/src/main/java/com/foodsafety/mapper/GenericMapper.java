package com.foodsafety.mapper;

import java.util.List;
import java.util.Set;

public interface GenericMapper <T, I> {

    List<T> fromEntitiesToDTOList(List<I> i);

    List<I> fromDTOListToEntities(List<T> t);


    T fromEntityToDTO(I i);

    I fromDTOToEntity(T t);
}