package com.foodsafety.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;


@Entity
@Table(name = "CampaignRequest")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class CampaignRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_campRequest;
    private String requestStatus;
    private String detailedStatus;
    private float totalAmount;
    @Column(name = "current_date_and_time")
    private Date creationDate;

    private String campaignName;
    private String collecte;
    private String recuperation;
    private Date campaignStartDate;
    private Date compaignEndDate;
    // added in Jul 13, 2023
    private String comment;
    private String campaignRequestNumber;
    private String publicVariable;
    private String groupId;

    
    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<Beneficiary>Beneficiaries;
    
    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<Donor>Donateurs;

    @JsonIgnore
    @OneToMany(mappedBy = "campaignRequest")
    private List<TemporaryExpense> temporaryExpenses;
    

    @OneToMany(cascade = CascadeType.ALL)
    private List<CampaignRequestContainsProduct> campaignRequestContainsProducts;
    
    @OneToMany(mappedBy = "campaignRequest")
	private List<Transaction> transactions;
    
    private String nomenclatureCmp;



}