package com.b12.inventory.inventoryService.service;

import java.util.List;

import com.b12.inventory.inventoryService.model.Inventory;

public interface InventoryService {

	public List<Inventory> getAllProducts();
	
	public Inventory getProductByName(String name);
	
	public List<Inventory> getProductByGroup(String group);
	
	public List<Inventory> getProductByType(String type);
	
	public Inventory createProduct(Inventory inventory);
	
	public Inventory updateProduct(Inventory inventory);
	
	public void deleteProduct(Inventory inventory);
	
	public Inventory checkProductAvailability(String name);
	
	public Inventory productAvailabilityNotification(String name);
}
