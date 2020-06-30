package com.ms.CatalogueService.Models;

import com.ms.CatalogueService.Entities.Catalogue;

public class PriceInfo {
	
	private double actualPrice;
	private String offer;
	private double discountedPrice;
	
	private Catalogue product;

	public double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Catalogue getProduct() {
		return product;
	}

	public void setProduct(Catalogue product) {
		this.product = product;
	}
	
}
