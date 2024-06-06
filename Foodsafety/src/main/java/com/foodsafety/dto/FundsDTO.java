package com.foodsafety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundsDTO {

    private Long fundsId;

    private String donorId;

    private float currentAmount;

    private Date versDate;

    private String groupId;



    @JsonIgnoreProperties
    private List<TransactionDTO> transactionDTOS;

    public FundsDTO(Long fundId, String donor, float currentAmount, Date versDate, String groupId) {
        this.fundsId = fundId;
        this.donorId = donor;
        this.currentAmount = currentAmount;
        this.versDate = versDate ;
        this.groupId = groupId;
    }
}
