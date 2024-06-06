package com.foodsafety.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="CampaignRequestContainsProduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRequestContainsProduct {

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
    @JoinColumn(name = "id_campRequest")
    private  CampaignRequest campaignRequest;
}
