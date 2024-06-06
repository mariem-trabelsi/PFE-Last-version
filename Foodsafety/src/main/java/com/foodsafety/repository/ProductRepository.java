package com.foodsafety.repository;

import com.foodsafety.dto.ProductDTO;
import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.model.Product;
import com.foodsafety.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value = "SELECT new com.foodsafety.dto.ProductDTO" +
	        "(p.productId,  p.productName,  p.unitPrice, p.unit, p.groupId, p.subCategory.subCategoryId) " +
	        "FROM Product p " +
	        "WHERE p.subCategory.subCategoryId = :subCategoryId")
	List<ProductDTO> findBySubCategorySubCategoryId(@Param("subCategoryId") Long subCategoryId);

    //List<ProductDTO> findBySubCategorySubCategoryId(Long id);
    List<Product> findBySubCategorySubcategoryName(String nom);

	public List<Product> findAllByGroupId(String groupId);

    @Query(value = "SELECT new com.foodsafety.dto.ProductDTO" +
			"(p.productId,  p.productName,  p.unitPrice, p.unit, p.groupId, p.subCategory.subCategoryId) from Product p")
    List<ProductDTO> findAllAsDto();
}
