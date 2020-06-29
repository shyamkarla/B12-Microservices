package com.b12.inventory.inventoryService.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.b12.inventory.inventoryService.model.Inventory;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class InventoryRepositoryTest {



	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Test
	void saveCusomers() {
		
		
		
	}
	@Test
	void getAllProducts() {
		
		Iterable<Inventory> products = inventoryRepository.findAll();
		assertNotNull(products);
			
	}
	
	@Test
	void getProductByGroup() {
		
		Iterable<Inventory> products = inventoryRepository.findByProductGroup("Electronics");
		assertNotNull(products);
		for (Inventory product : products) {
			assertEquals("Electronics",product.getProductGroup());
		}
	}
	
	@Test
	void getProductByType() {
		
		Iterable<Inventory> products = inventoryRepository.findByProductType("Mobile");
		assertNotNull(products);
		for (Inventory product : products) {
			assertEquals("Mobile",product.getProductType());
		}
	}

	@Test
	void getProductByName() {
		
		Inventory product = inventoryRepository.findByProductName("LG");
		assertNotNull(product);
		assertEquals("LG",product.getProductName());
	}



}
