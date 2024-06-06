package com.foodsafety.repository;

import com.foodsafety.dto.BeneficiaryRequestContainsProductDTO;
import com.foodsafety.model.BeneficiaryRequestContainsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeneficiaryRequestContainsProductRepository extends JpaRepository<BeneficiaryRequestContainsProduct, Long> {


    @Query(value = "SELECT new com.foodsafety.dto.BeneficiaryRequestContainsProductDTO(r.id, r.quantity, r.comment, r.size, r.product.productId, r.beneficiaryRequest.id_benfRequest) from BeneficiaryRequestContainsProduct  r")
    List<BeneficiaryRequestContainsProductDTO> findAllAsDto();

     @Query(value = "SELECT new com.foodsafety.dto.BeneficiaryRequestContainsProductDTO(r.id, r.quantity, "
     		+ "r.comment, r.size, r.product.productId, r.beneficiaryRequest.id_benfRequest) "
     		+ "from BeneficiaryRequestContainsProduct  r WHERE r.beneficiaryRequest.id_benfRequest = :id")
    List<BeneficiaryRequestContainsProductDTO> findByBeneficiaryRequestIdBenfRequest(@Param("id")Long id);

}
