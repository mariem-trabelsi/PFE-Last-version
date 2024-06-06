package com.foodsafety.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "SubCategory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;
    private String subcategoryName;
    private String groupId;

    @JsonBackReference
    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subCategory")
    private List<Product> products;
    
    public List<Product> getProducts() {
		return products;
	}




}
