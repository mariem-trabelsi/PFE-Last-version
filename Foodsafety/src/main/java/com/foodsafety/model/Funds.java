package com.foodsafety.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Funds")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundsId;

    private String donorId;

    private float currentAmount;


    private Date versDate;

    private String groupId;


    @OneToMany(mappedBy = "funds")
    private List<Transaction> transactions;


}
