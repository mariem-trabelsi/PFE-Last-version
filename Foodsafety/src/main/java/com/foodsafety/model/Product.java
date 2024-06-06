package com.foodsafety.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name="Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String productName;
	private float unitPrice;
	private String unit;
	private String groupId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_subCategory")
	private SubCategory subCategory;





	 
}
