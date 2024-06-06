package com.foodsafety.repository;

import com.foodsafety.dto.FundsDTO;
import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.foodsafety.model.Funds;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface FundsRepository extends JpaRepository <Funds, Long> {

    @Query(value =
            "SELECT new com.foodsafety.dto.FundsDTO(f.fundsId, f.donorId, f.currentAmount,f.versDate,f.groupId) from Funds  f")
    List<FundsDTO> findAllAsDto();

    Funds findByDonorId(String donorId);

    List<Funds> findAllByGroupId(String groupId);

}
