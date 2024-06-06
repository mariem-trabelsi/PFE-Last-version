package com.foodsafety.repository;

import com.foodsafety.dto.TemporaryExpenseDTO;
import com.foodsafety.model.BeneficiaryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.foodsafety.model.TemporaryExpense;

import java.util.List;

@Repository
public interface TemporaryExpenseRepository extends JpaRepository<TemporaryExpense,Long>  {


    @Query(value = "SELECT new com.foodsafety.dto.TemporaryExpenseDTO(t.temporaryExpenseId, " +
            "t.temporaryExpenseAmount,t.comment, t.contributionDate, t.groupId," +
            "t.beneficiaryRequest.id_benfRequest, t.campaignRequest.id_campRequest, t.id_donor) " +
            "FROM TemporaryExpense t")
    List<TemporaryExpenseDTO> findAllAsDto();



    @Query(value = "SELECT t FROM TemporaryExpense t WHERE t.campaignRequest.id_campRequest = :campId")
    List<TemporaryExpense> findByCampID(@Param("campId") Long id);

    @Query(value = "SELECT t FROM TemporaryExpense t WHERE t.id_donor = :donorID")
    List<TemporaryExpense> findByDonorID(@Param("donorID") String id);


    boolean existsByBeneficiaryRequest(BeneficiaryRequest beneficiaryRequest);

    List<TemporaryExpense> findAllByGroupId(String groupId);

}
