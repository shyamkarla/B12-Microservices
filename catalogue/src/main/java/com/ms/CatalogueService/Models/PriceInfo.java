package com.ms.CatalogueService.Models;

import com.ms.CatalogueService.Entities.Catalogue;

public class PriceInfo {
	
	private double productPrice;
	private double discountPercentage;
	private double discountedPrice;
	
	private Catalogue product;


	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
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
