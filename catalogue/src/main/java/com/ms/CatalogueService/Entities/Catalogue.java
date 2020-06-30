package com.ms.CatalogueService.Entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name ="product")
public class Catalogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(cascade=CascadeType.ALL,mappedBy = "catalogue")
    private List<CatalogueReviews> catalogueReviewsList;

    public List<CatalogueReviews> getCatalogueReviews() {
        return catalogueReviewsList;
    }

    public void setCatalogueReviews(List<CatalogueReviews> catalogueReviewsList) {
        this.catalogueReviewsList = catalogueReviewsList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
