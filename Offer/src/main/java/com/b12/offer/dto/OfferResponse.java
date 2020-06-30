package com.b12.offer.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class OfferResponse {
	@JsonInclude(Include.NON_NULL)
	private Long offerId;
	@JsonInclude(Include.NON_NULL)
	private Float offerPrice;
	@JsonInclude(Include.NON_NULL)
	private String offerCategory;
	@JsonInclude(Include.NON_NULL)
	private LocalDate validUpto;
	@JsonInclude(Include.NON_NULL)
	private LocalDate ValidFrom;
	private boolean result;
	private String message;
}
