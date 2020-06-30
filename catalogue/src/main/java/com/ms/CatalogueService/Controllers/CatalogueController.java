package com.ms.CatalogueService.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.ms.CatalogueService.Entities.Catalogue;
import com.ms.CatalogueService.Exceptions.NoProductFoundException;
import com.ms.CatalogueService.Exceptions.ProductOutOfStockException;
import com.ms.CatalogueService.Models.InventoryInfo;
import com.ms.CatalogueService.Models.PriceInfo;
import com.ms.CatalogueService.Services.CatalogueService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/catalogue")
@Api(value="Meru online store", description="Operations pertaining to Catalogue Service") 
public class CatalogueController {

    @Autowired
    CatalogueService catalogueService;

    @Autowired
    EurekaClient eurekaClient;
    
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
    
    @ApiOperation(value = "View a list of available products", response = Catalogue.class, tags = "GetAllProducts")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
    @GetMapping("/allproducts")
    public List<Catalogue> getAllProducts(){
        List<Catalogue> catalogue = catalogueService.getAllProducts();
        return catalogue;
    }

    @ApiOperation(value = "View a product", response = Catalogue.class, tags = "getProduct")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved product"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
    @GetMapping("/products/{id}")
    public Catalogue getProduct(@PathVariable int id){
        Catalogue product = catalogueService.getProduct(id);
        return product;
    }

    @ApiOperation(value = "Add a product", response = Catalogue.class, tags = "Add new Product")
   	@ApiResponses(value = {
   	        @ApiResponse(code = 201, message = "Successfully added product"),
   	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
   	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
   	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
   	})
    @PostMapping("/products")
    public Catalogue addNewProduct(@RequestBody Catalogue catalogue) {
        Catalogue newProduct = catalogueService.addNewProduct(catalogue);
        return newProduct;
    }

    @ApiOperation(value = "Delete a product", response = Catalogue.class, tags = "Delete a Product")
   	@ApiResponses(value = {
   	        @ApiResponse(code = 200, message = "Successfully deleted product"),
   	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
   	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
   	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
   	})
    @DeleteMapping("/products/{id}")
    public Catalogue deleteProduct(@PathVariable int id) {
        Catalogue deleteProduct = catalogueService.deleteProduct(id);
        return deleteProduct;
    }
    
    @ApiOperation(value = "Update a product", response = Catalogue.class, tags = "Update new Product")
   	@ApiResponses(value = {
   	        @ApiResponse(code = 200, message = "Successfully updated product"),
   	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
   	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
   	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
   	})
    @PutMapping("/products/{id}")
    public Catalogue updateProduct(@PathVariable int id, @RequestBody Catalogue catalogue) {
        Catalogue updateProduct = catalogueService.updateProduct(id, catalogue);
        return updateProduct;
    }

//other APIs which would consume other microservices

    @GetMapping("/products/{id}/availability")
    public InventoryInfo isAvailable(@PathVariable int id){
    
    	Catalogue product = catalogueService.getProduct(id);
    	if(product==null) {
    		throw new NoProductFoundException("No Such Product found in Catalogue!");
    	}
    	RestTemplate restTemplate = restTemplateBuilder.build();
    	
    	//using Eureka Server to get Inventory app
    	InstanceInfo info = eurekaClient.getNextServerFromEureka("inventory-service", false);
    	String baseURL = info.getHomePageUrl();
    	baseURL = baseURL+"inventory/available/"+product.getProductName();
    	
    	InventoryInfo inventoryInfo = restTemplate.getForObject(baseURL, InventoryInfo.class);
    	
    	if(inventoryInfo==null) {
    		throw new ProductOutOfStockException("Product is out of Stock!");
    	}
    	inventoryInfo.setProduct(product);
    	
        return inventoryInfo;
    }

    @GetMapping("/products/{id}/price")
    public PriceInfo getProductPrice(@PathVariable int id){
    	Catalogue product = catalogueService.getProduct(id);
    	RestTemplate restTemplate = restTemplateBuilder.build();
    	
    	//using Eureka Server to get Inventory app
    	InstanceInfo info = eurekaClient.getNextServerFromEureka("price-service", false);
    	String baseURL = info.getHomePageUrl();
    	baseURL = baseURL+"price/"+id;
    	PriceInfo priceInfo = restTemplate.getForObject(baseURL, PriceInfo.class);
    	priceInfo.setProduct(product);
        return priceInfo;
    }
    
/*
    @GetMapping("/products/filter")
    public List<Product> filterProduct(@RequestBody Filters filter){
        List<Product> products = service.filterProducts(filter);
        return products;
    }

    @PostMapping("/products/{id}/addToWishlist")
    public List<Product> addToWishlist(@PathVariable int id) {
        Product product = service.getProduct(id);
        List<Product> wishlist = uService.addToWishlist(product);
        return wishlist;
    }

    @PostMapping("/products/{id}/addToCart")
    public List<Product> addToCart(@PathVariable int id) {
        Product product = service.getProduct(id);
        List<Product> cart = uService.addToCart(product);
        return cart;
    }
        */


}

