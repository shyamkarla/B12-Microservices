package com.ms.CatalogueService.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.ms.CatalogueService.Entities.Catalogue;
import com.ms.CatalogueService.Services.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/catalogue")
public class CatalogueController {

    @Autowired
    CatalogueService catalogueService;

/*    @Autowired
    UserService uService;*/

    @GetMapping("/allproducts")
    public List<Catalogue> getAllProducts(){
        List<Catalogue> catalogue = catalogueService.getAllProducts();
        return catalogue;
    }

    @GetMapping("/products/{id}")
    public Catalogue getProduct(@PathVariable int id){
        Catalogue product = catalogueService.getProduct(id);
        return product;
    }

    @PostMapping("/products")
    public Catalogue addNewProduct(@RequestBody Catalogue catalogue) {
        Catalogue newProduct = catalogueService.addNewProduct(catalogue);
        return newProduct;
    }

    @DeleteMapping("/products/{id}")
    public Catalogue deleteProduct(@PathVariable int id) {
        Catalogue deleteProduct = catalogueService.deleteProduct(id);
        return deleteProduct;
    }
    @PutMapping("/products/{id}")
    public Catalogue updateProduct(@PathVariable int id, @RequestBody Catalogue catalogue) {
        Catalogue updateProduct = catalogueService.updateProduct(id, catalogue);
        return updateProduct;
    }

//other APIs which would consume other microservices


/*
    @GetMapping("/products/{id}/price")
    public Product getProductPrice(@PathVariable int id){
        Product product = service.getProductPrice(id);
        return product;
    }

    @GetMapping("/products/{id}/offers")
    public Product getProductOffer(@PathVariable int id){
        Product product = service.getProductOffer(id);
        return product;
    }

    @GetMapping("/products/filter")
    public List<Product> filterProduct(@RequestBody Filters filter){
        List<Product> products = service.filterProducts(filter);
        return products;
    }

    @GetMapping("/products/{id}/availability")
    public Product isAvailable(@PathVariable int id){
        Product product = service.isAvailable(id);
        return product;
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

