package com.foodsafety.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="TemporaryExpense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryExpense implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long temporaryExpenseId;
	private float temporaryExpenseAmount;
	private String comment;
	private Date contributionDate;
    private String groupId;

    @OneToOne
    @JoinColumn(name = "id_benfRequest")
    private BeneficiaryRequest beneficiaryRequest;

    @ManyToOne
    @JoinColumn(name = "id_campRequest")
    private CampaignRequest campaignRequest;

    private String id_donor;

}
