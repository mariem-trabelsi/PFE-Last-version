package com.foodsafety.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BeneficiaryRequest")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_benfRequest;
    private String beneficiaryRequestNumber;
    private String requestStatus;
    private String detailedStatus;
    private float totalAmount;
    private Date creationDate;
    private String id_donor;
    private String comment;
    private String publicVariable;
    private String id_beneficiary;
    private String groupId;


    @OneToOne(mappedBy = "beneficiaryRequest")
    private TemporaryExpense temporaryExpense;


    @OneToMany(cascade = CascadeType.ALL)
    private List<BeneficiaryRequestContainsProduct> beneficiaryRequestContainsProducts;
    
    @OneToOne(mappedBy = "beneficiaryRequest")
	private Transaction transaction;
    
    public TemporaryExpense getTemporaryExpense() {
		return temporaryExpense;
	}
    
    private String nomenclature;


}
