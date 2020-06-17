package com.b12.inventory.inventoryService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b12.inventory.inventoryService.model.Inventory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/")
@Api(value="meru online store", description="Operations pertaining to Inventoory Service") 
@RefreshScope
public class InventoryController {
	
	@Value("${message:Hello Default}")
	private String message;
	
	@Value("${user.role: Default role}")
	private String role;
	
	
	@ApiOperation(value = "View a list of available products", response = Inventory.class, tags = "GetAllProducts")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping(value = "/products")
	   public List<Inventory> getInventory() {
		
		List<Inventory> listofProducts=new ArrayList<>();
		Inventory inventory =new Inventory();
		
		inventory.setProductId(1);
		inventory.setProductName("Samsung LED TV");
		inventory.setQuantity(100);
		inventory.setItemType("TV");
		inventory.setItemGroup("Entertainement");
		listofProducts.add(inventory);
	      return listofProducts;
	   }
		@GetMapping(value = "/message")
	   public String home() {
	      return "Inventory Service message:"+message+"---role :"+role;
	   }
	
		@GetMapping(value = "/user/{name}")
		
	   public String userLogged(@PathVariable String name) {
	      return "Inventory Service user name :"+name;
	   }
}
