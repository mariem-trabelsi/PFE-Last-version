package com.foodsafety.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodsafety.model.Beneficiary;
import com.foodsafety.model.Donor;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CampaignRequestDTO {

    private Long id_campRequest;
    private String requestStatus;
    private String detailedStatus;
    private String campaignName;
    private Date campaignStartDate;
    
    private float totalAmount;

    private String collecte;

    private String recuperation;

    private Date creationDate;

    private Date compaignEndDate;

    private List<Beneficiary> beneficiaries;
    private List<Donor> donateurs;

    // added in Jul 13, 2023
    private String comment;
    private String campaignRequestNumber;
    private String publicVariable;

    private String groupId;


    @JsonIgnoreProperties
    private List<TemporaryExpenseDTO> temporaryExpenseDTOS;

    @JsonIgnoreProperties
    private List<CampaignRequestContainsProductDTO> campaignRequestContainsProductDTOS;
    
    @JsonIgnoreProperties
    private List<TransactionDTO> transactionDTOS;

    // added in Jul 20, 2023
    private String nomenclatureCmp;

}
