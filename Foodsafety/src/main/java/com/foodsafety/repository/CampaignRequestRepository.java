package com.foodsafety.repository;


import com.foodsafety.model.BeneficiaryRequest;
import com.foodsafety.model.CampaignRequest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface CampaignRequestRepository extends JpaRepository<CampaignRequest,Long> {

	List<CampaignRequest> findByRequestStatus(String status);

	CampaignRequest findByPublicVariable(String publicVariable);

	@Query("SELECT MAX(YEAR(cr.creationDate)) FROM CampaignRequest cr")
    Long findLastYear();

    @Query("SELECT MAX(cr.id_campRequest) FROM CampaignRequest cr")
    Long findLastId();

    public List<CampaignRequest> findAllByGroupId(String groupId);

    List<CampaignRequest> findByRequestStatusAndGroupId(String status, String groupId);
}
