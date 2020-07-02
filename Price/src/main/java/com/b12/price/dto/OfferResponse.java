package com.b12.price.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OfferResponse {

	private long offerId;
	private float offerPercentage;
	private String offerCategory;
	private LocalDate validUpto;
	private LocalDate ValidFrom;
	private boolean result;
	private String message;
}
