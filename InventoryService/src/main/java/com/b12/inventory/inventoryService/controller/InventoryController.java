package com.b12.inventory.inventoryService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b12.inventory.inventoryService.model.Inventory;
import com.b12.inventory.inventoryService.service.InventoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/inventory")
@Api(value = "meru online store", description = "Operations pertaining to Inventoory Service")
@RefreshScope
public class InventoryController {

	@Value("${message:Hello Default}")
	private String message;

	@Value("${user.role: Default role}")
	private String role; 
	
	@Autowired
	InventoryService inventoryService;

	@ApiOperation(value = "View a list of available products", response = Inventory.class, tags = "GetAllProducts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping
	public List<Inventory> getInventory() {

		List<Inventory> listofProducts = new ArrayList<>();
		listofProducts= inventoryService.getAllProducts();
		return listofProducts;
	}

	@ApiOperation(value = "View a list of products by Name", response = Inventory.class, tags = "Get Products By Name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved products by Name"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/{name}")
	public Inventory getInventoryByName(@PathVariable String name) {
		return inventoryService.getProductByName(name);
	}
	
	
	@ApiOperation(value = "View a list of products by group", response = Inventory.class, tags = "Get Products By Group")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved products by Group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/group/{group}")
	public List<Inventory> getInventoryByGroup(@PathVariable String group) {

		return inventoryService.getProductByGroup(group);
	}

	@ApiOperation(value = "View a list of products by Type", response = Inventory.class, tags = "Get Products By Type")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved products by Type"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("type/{type}")
	public List<Inventory> getInventoryByType(@PathVariable String type) {

		return inventoryService.getProductByType(type);
	}
	@ApiOperation(value = "create new products in inventory", response = Inventory.class, tags = "Create Product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created product in inventory"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping
	public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
		Inventory response=inventoryService.createProduct(inventory);
		
		return new ResponseEntity<Inventory>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "update products in inventory", response = Inventory.class, tags = "Update Product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated product in inventory"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PutMapping
	public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inventory) {
			Inventory response=inventoryService.updateProduct(inventory);
		
		return new ResponseEntity<Inventory>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "delete products in inventory", response = Inventory.class, tags = "Delete Product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted product in inventory"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@DeleteMapping
	public ResponseEntity<String> deleteInventory(@RequestBody Inventory inventory) {
		inventoryService.deleteProduct(inventory);
		
		return new ResponseEntity<String>("product deleted successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Product availability in inventory", response = Inventory.class, tags = "Availability of Product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Availability of product in inventory"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/available/{name}")
	public ResponseEntity<Inventory> productAvailabilityCheck(@PathVariable String name) {
		Inventory response=inventoryService.checkProductAvailability(name);
		
		return new ResponseEntity<Inventory>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Product availability notification to user", response = Inventory.class, tags = "Notify Availability")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notify Availability of product to user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/notify/{name}")
	public ResponseEntity<Inventory> productAvailabilityNotification(@PathVariable String name) {
		Inventory response=inventoryService.productAvailabilityNotification(name);
		
		return new ResponseEntity<Inventory>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/message")
	public String home() {
		return "Inventory Service message:" + message + "---role :" + role;
	}

	@GetMapping(value = "/user/{name}")

	public String userLogged(@PathVariable String name) {
		return "Inventory Service user name :" + name;
	}
}
