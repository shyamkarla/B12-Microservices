package com.b12.offer.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Offer {

	@Id
	private long offerId;
	private float offerPrice;
	private String offerCategory;
	private LocalDate validUpto;
	private LocalDate ValidFrom;
}
