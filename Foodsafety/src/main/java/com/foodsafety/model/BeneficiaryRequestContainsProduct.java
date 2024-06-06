package com.foodsafety.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
//import java.util.Collection;
//import java.util.List;

@Entity
@Table(name="BeneficiaryRequestContainsProduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryRequestContainsProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String comment;
    private String size;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private  Product product;

    @ManyToOne
    @JoinColumn(name = "id_benfRequest")
    private  BeneficiaryRequest beneficiaryRequest;







}
