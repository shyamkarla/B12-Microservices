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
	private Float offerPercentage;
	@JsonInclude(Include.NON_NULL)
	private String offerCategory;
	@JsonInclude(content = Include.NON_NULL)
	private LocalDate validUpto;
	@JsonInclude(content = Include.NON_NULL)
	private LocalDate ValidFrom;
	@JsonInclude(content = Include.NON_NULL)
	private Boolean result;
	@JsonInclude(Include.NON_NULL)
	private String message;
}
