package com.foodsafety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class TransactionDTO {

    private Long transactionId;
    private Float transactionAmount;
    private Boolean transactionType;
    private Date transactionDate;
    private String comment;
    private String groupId;

    @JsonIgnoreProperties
    private Long id_funds;

    
    private Long id_benfRequest;
    
   
    private Long id_campRequest;

    public TransactionDTO (Long transactionId, Float transactionAmount, Boolean transactionType, Date transactionDate, String comment, String groupId, Long id_funds, Long id_benfRequest, Long id_campRequest) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.comment = comment;
        this.groupId = groupId;
        this.id_funds = id_funds;
        this.id_benfRequest = id_benfRequest;
        this.id_campRequest = id_campRequest;
    }
}
