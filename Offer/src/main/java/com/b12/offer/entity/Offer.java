package com.b12.offer.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Offer {

	@Id
	@GeneratedValue
	private long offerId;
	private Float offerPercentage;
	private String offerCategory;
	private LocalDate validUpto;
	private LocalDate ValidFrom;
}
