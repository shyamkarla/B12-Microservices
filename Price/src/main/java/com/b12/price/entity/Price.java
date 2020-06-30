package com.b12.price.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Price {
	@Id
	private Long productId;
	private Float productPrice;
	private Float discountPercentage;
	private Long offerId;	
}
