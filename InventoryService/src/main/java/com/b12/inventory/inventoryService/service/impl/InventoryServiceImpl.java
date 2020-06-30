package com.b12.inventory.inventoryService.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b12.inventory.inventoryService.model.Inventory;
import com.b12.inventory.inventoryService.repository.InventoryRepository;
import com.b12.inventory.inventoryService.service.InventoryService;
@Service
public class InventoryServiceImpl implements InventoryService{

	
	@Autowired
	InventoryRepository iRepo;
	
	@Override
	public List<Inventory> getAllProducts() {
		Iterable<Inventory> iList=iRepo.findAll();
		List<Inventory> prodList=new ArrayList<>();
		for (Inventory inventory : iList) {
			prodList.add(inventory);
			
		}
		return prodList;
	}

	@Override
	public Inventory getProductByName(String name) {
		Inventory inventory=iRepo.findByProductName(name);
		return inventory;
	}

	@Override
	public List<Inventory> getProductByGroup(String group) {
		Iterable<Inventory> iList=iRepo.findByProductGroup(group);
		List<Inventory> prodList=new ArrayList<>();
		for (Inventory inventory : iList) {
			prodList.add(inventory);
			
		}
		return prodList;
	}

	@Override
	public List<Inventory> getProductByType(String type) {
		Iterable<Inventory> iList=iRepo.findByProductType(type);
		List<Inventory> prodList=new ArrayList<>();
		for (Inventory inventory : iList) {
			prodList.add(inventory);
			
		}
		return prodList;
	}

	@Override
	public Inventory createProduct(Inventory inventory) {
		
		return iRepo.save(inventory);
	}

	@Override
	public Inventory updateProduct(Inventory inventory) {
		return iRepo.save(inventory);
	}

	@Override
	public void deleteProduct(Inventory inventory) {
		iRepo.delete(inventory);
	}

	@Override
	public Inventory checkProductAvailability(String name) {
		
		return iRepo.findProductAvailable(name);
	}

	@Override
	public Inventory productAvailabilityNotification(String name) {
		// TODO Auto-generated method stub
		return iRepo.productAvailabilityNotification(name);
	}

}
