package com.foodsafety.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Boolean transactionType;

    private Float transactionAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    private String comment;
    private String groupId;
    
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "id_benfRequest")
    private BeneficiaryRequest beneficiaryRequest;
    
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_campRequest")
    private CampaignRequest campaignRequest;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_funds")
    private Funds funds;

}
