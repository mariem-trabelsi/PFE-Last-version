package com.foodsafety.repository;

import com.foodsafety.dto.TransactionDTO;
import com.foodsafety.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foodsafety.model.BeneficiaryRequest;
import com.foodsafety.model.CampaignRequest;
import com.foodsafety.model.Funds;
import com.foodsafety.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT new com.foodsafety.dto.TransactionDTO(t.transactionId, t.transactionAmount, t.transactionType, t.transactionDate,t.comment, t.groupId, t.funds.fundsId, t.beneficiaryRequest.id_benfRequest, t.campaignRequest.id_campRequest) from Transaction  t")
    List<TransactionDTO> findAllAsDto();

	boolean existsByBeneficiaryRequest(BeneficiaryRequest beneficiaryRequest);

    List<Transaction> findAllByGroupId(String groupId);

	
	List<Transaction> findAllByFunds(Funds funds);
	
	List<Transaction> findAllByBeneficiaryRequest(BeneficiaryRequest ben);
	
	List<Transaction> findAllByCampaignRequest(CampaignRequest ben);
}
