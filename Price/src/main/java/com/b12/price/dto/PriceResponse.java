package com.b12.price.dto;

import lombok.Data;

@Data
public class PriceResponse {
	private Long productId;
	private float productPrice;
	private float discountedPrice;
	private float discountPercentage;
}
