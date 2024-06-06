package com.foodsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodsafety.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category getReferenceById(Long id);

    // adding the groupId condition

    //public Category getCategoryByCategoryIdAndGroupId(@Param("categoryId") Long categoryId, @Param("groupId") String groupId);

    public List<Category> findAllByGroupId(String groupId);



}
