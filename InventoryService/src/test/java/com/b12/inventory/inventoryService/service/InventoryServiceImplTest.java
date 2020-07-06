package com.b12.inventory.InventoryService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.b12.inventory.inventoryService.model.Inventory;
import com.b12.inventory.inventoryService.repository.InventoryRepository;
import com.b12.inventory.inventoryService.service.impl.InventoryServiceImpl;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class InventoryServiceImplTest {

	@InjectMocks
	InventoryServiceImpl inventoryService;
	
	@Mock
	InventoryRepository inventoryRepo;
	
	@Test
	public void createinventoryTest() {
		
		Inventory inventory=new Inventory();
		inventory.setProductId(1);
		inventory.setProductName("Apple IPhone XE");
		inventory.setProductGroup("Electronics");
		inventory.setProductType("Mobile");
		inventory.setQuantity(44);
		inventory.setAvailable(true);
		
		when(inventoryRepo.save(inventory)).thenReturn(inventory);
		
		Inventory response=inventoryService.createProduct(inventory);
		assertEquals(inventory.getProductName(),response.getProductName());
		assertEquals(inventory.getQuantity(),response.getQuantity());
		
	}
	
	@Test
	public void findByProductGroupTest() {
		Inventory inventory=new Inventory();
		inventory.setProductId(1);
		inventory.setProductName("Apple IPhone XE");
		inventory.setProductGroup("Electronics");
		inventory.setProductType("Mobile");
		inventory.setQuantity(44);
		inventory.setAvailable(true);
		
		List<Inventory> products =new ArrayList<>();
		products.add(inventory);
		
		when(inventoryRepo.findByProductGroup("Electronics")).thenReturn(products);
		
		List<Inventory> response=inventoryService.getProductByGroup("Electronics");
		assertEquals(response.get(0).getProductGroup(),"Electronics");
		
	}
	
	@Test
	public void findByProductTypeTest() {
		
		Inventory inventory=new Inventory();
		inventory.setProductId(1);
		inventory.setProductName("Apple IPhone XE");
		inventory.setProductGroup("Electronics");
		inventory.setProductType("Mobile");
		inventory.setQuantity(44);
		inventory.setAvailable(true);
		
		List<Inventory> products =new ArrayList<>();
		products.add(inventory);
		
		when(inventoryRepo.findByProductType("Mobile")).thenReturn(products);
		
		List<Inventory> response=inventoryService.getProductByType("Mobile");
		assertEquals(response.get(0).getProductType(),"Mobile");
	}
	
	@Test
	public void findByProductNameTest() {
		
		Inventory inventory=new Inventory();
		inventory.setProductId(1);
		inventory.setProductName("Apple IPhone XE");
		inventory.setProductGroup("Electronics");
		inventory.setProductType("Mobile");
		inventory.setQuantity(44);
		inventory.setAvailable(true);
		
		
		
		when(inventoryRepo.findByProductName("Apple IPhone XE")).thenReturn(inventory);
		
		Inventory response=inventoryService.getProductByName("Apple IPhone XE");
		assertEquals(response.getProductName(),"Apple IPhone XE");
	}

	

}
