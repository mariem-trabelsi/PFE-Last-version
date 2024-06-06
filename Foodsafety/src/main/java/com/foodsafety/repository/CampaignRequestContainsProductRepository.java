package com.foodsafety.repository;

import com.foodsafety.dto.CampaignRequestContainsProductDTO;
import com.foodsafety.model.CampaignRequestContainsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignRequestContainsProductRepository extends JpaRepository<CampaignRequestContainsProduct, Long> {
	
	 @Query(value = "SELECT new com.foodsafety.dto.CampaignRequestContainsProductDTO(r.id, r.quantity, "
	 		+ "r.comment, r.size, r.product.productId, r.campaignRequest.id_campRequest) "
	 		+ "from CampaignRequestContainsProduct  r")
	    List<CampaignRequestContainsProductDTO> findAllAsDto();

	 @Query(value = "SELECT new com.foodsafety.dto.CampaignRequestContainsProductDTO(r.id, r.quantity, "
	     		+ "r.comment, r.size, r.product.productId, r.campaignRequest.id_campRequest) "
	     		+ "from CampaignRequestContainsProduct  r WHERE r.campaignRequest.id_campRequest = :id")
    List<CampaignRequestContainsProductDTO> findByCampaignRequestId(@Param("id")Long id);
}
