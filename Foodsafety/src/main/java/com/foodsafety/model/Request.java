package com.foodsafety.model;
/*
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString

public class Request {

    @Id
    @EqualsAndHashCode.Include // inclure id dans la génération de equals et hashCode
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String requestStatus;
    private float totalAmount;
    @Column(name = "current_date_and_time")
    private Date creationDate;

    private String typeRequest;
    private Long beneficiaryRequestNumber;

    private String campaignName;
    private Date campaignStartDay;
    //private List <String> beneficiaries;

    //private Set<String>Donors;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected List<TemporaryExpenseBen> temporaryExpensBens;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<BeneficiaryRequestContainsProduct> beneficiaryRequestContainsProducts;


}*/
