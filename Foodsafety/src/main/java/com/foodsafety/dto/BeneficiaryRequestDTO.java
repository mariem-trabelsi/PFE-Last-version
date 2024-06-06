package com.foodsafety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.foodsafety.model.TemporaryExpense;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BeneficiaryRequestDTO {

    private Long id_benfRequest;
    private String beneficiaryRequestNumber;
    private String requestStatus;
    private String detailedStatus;
    private float totalAmount;
    private Date creationDate;
    private String id_beneficiary;
    private String id_donor;
    private String comment;
    private String publicVariable;
    private String groupId;

    @JsonIgnoreProperties
    private TemporaryExpenseDTO temporaryExpenseDTO;

    @JsonIgnoreProperties
    private List<BeneficiaryRequestContainsProductDTO> beneficiaryRequestContainsProductDTOS;
    
    private String nomenclature;


}
