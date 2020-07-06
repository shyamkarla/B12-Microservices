package com.ms.CatalogueService.Models;

import com.ms.CatalogueService.Entities.Catalogue;

public class InventoryInfo {

	private boolean available;
	private int quantity;
	
	private Catalogue product;
	
	public Catalogue getProduct() {
		return product;
	}
	public void setProduct(Catalogue product) {
		this.product = product;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean isAvailable) {
		this.available = isAvailable;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
