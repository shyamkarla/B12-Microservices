package com.ms.CatalogueService.Entities;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "catalogue")
    private CatalogueReviews catalogueReviews;

    public CatalogueReviews getCatalogueReviews() {
        return catalogueReviews;
    }

    public void setCatalogueReviews(CatalogueReviews catalogueReviews) {
        this.catalogueReviews = catalogueReviews;
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
