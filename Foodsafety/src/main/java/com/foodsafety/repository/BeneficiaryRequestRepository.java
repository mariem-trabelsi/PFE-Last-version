package com.foodsafety.repository;

import com.foodsafety.dto.BeneficiaryRequestDTO;
import com.foodsafety.dto.FundsDTO;
import com.foodsafety.model.BeneficiaryRequest;
import com.foodsafety.model.Category;
import com.foodsafety.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRequestRepository extends JpaRepository<BeneficiaryRequest,Long> {
	
	List<BeneficiaryRequest> findByRequestStatus(String status);

    List<BeneficiaryRequest> findByRequestStatusAndGroupId(String status, String groupId);

	BeneficiaryRequest findByPublicVariable(String publicVariable);
    
	@Query("SELECT MAX(YEAR(br.creationDate)) FROM BeneficiaryRequest br")
    Long findLastYear();

    @Query("SELECT MAX(br.id_benfRequest) FROM BeneficiaryRequest br")
    Long findLastId();

    public List<BeneficiaryRequest> findAllByGroupId(String groupId);

}
