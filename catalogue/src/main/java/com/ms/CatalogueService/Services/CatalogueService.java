package com.ms.CatalogueService.Services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.ms.CatalogueService.Entities.Catalogue;
import com.ms.CatalogueService.Exceptions.NoProductFoundException;
import com.ms.CatalogueService.Repositories.CatalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogueService {

    @Autowired
    CatalogueRepository catalogueRepository;

    public List<Catalogue> getAllProducts(){
        List<Catalogue> catalogue =  (List<Catalogue>)catalogueRepository
                .findAll();
        return catalogue;
    }

    public Catalogue getProduct(int id){
        Catalogue catalogue = catalogueRepository.findById(id).get();
        return catalogue;
    }

    public Catalogue addNewProduct(Catalogue prod) {
        return catalogueRepository.save(prod);
    }

    public Catalogue deleteProduct(int id){
        Catalogue catalogue = catalogueRepository.findById(id).get();
        if(catalogue==null) {
            throw new NoProductFoundException("Product ID not Found "+id);
        }
        catalogueRepository.delete(catalogue);
        return catalogue;
    }

    public Catalogue updateProduct(int id, Catalogue updatedProduct)  {
        Optional<Catalogue> catalogueById = catalogueRepository.findById(id);
        if(!catalogueById.isPresent()) {
            throw new NoProductFoundException("Product ID not Found "+id);
        }
        Catalogue catalogue = catalogueById.get();
        catalogue.setProductName(updatedProduct.getProductName());
        return catalogueRepository.save(catalogue);

    }

////other service methods would consume other microservices
/*
    public Catalogue getProductPrice(int id){
        Catalogue catalogue = repository.findById(id).get();


        return catalogue;
    }

    public Catalogue getProductOffer(int id){
        Catalogue catalogue = repository.findById(id).get();

        return catalogue;
    }

    public List<Catalogue> filterProducts(Filters filter){
        List<Catalogue> catalogue = (List<Catalogue>) repository.findAll();

        return catalogue;
    }

    public Catalogue isAvailable(int id){
        Catalogue catalogue = repository.findById(id).get();

        return catalogue;
    }

 */

}

