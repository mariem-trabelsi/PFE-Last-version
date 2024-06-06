package com.foodsafety.repository;

import com.foodsafety.dto.SubCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foodsafety.model.SubCategory;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query(value = "SELECT new com.foodsafety.dto.SubCategoryDTO(s.subCategoryId,s.subcategoryName,s.groupId,s.category.categoryId) from SubCategory  s")
    List<SubCategoryDTO> findAllAsDto();

    List<SubCategory> findByCategoryCategoryId(Long id);

    List<SubCategory> findAllByGroupId(String groupId);


}
