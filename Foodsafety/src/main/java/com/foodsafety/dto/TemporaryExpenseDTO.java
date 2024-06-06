package com.foodsafety.dto;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryExpenseDTO {

    private Long temporaryExpenseId;
    private float temporaryExpenseAmount;
    private String comment;
    private Date contributionDate;
    private String groupId;


    


    @JsonIgnoreProperties
    private Long id_benfRequest;

    @JsonIgnoreProperties
    private Long id_campRequest;

    private String id_donor;


   


}
