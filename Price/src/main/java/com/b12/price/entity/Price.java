package com.b12.price.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Price {
	@Id
	@GeneratedValue
	private Long productId;
	private Long offerId;	
	private Float productPrice;
}
