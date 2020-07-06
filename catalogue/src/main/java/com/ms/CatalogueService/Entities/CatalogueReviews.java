package com.ms.CatalogueService.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "product_reviews")
public class CatalogueReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "review_id")
    private int reviewId;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="product_id", referencedColumnName = "id")
    private Catalogue catalogue;

    @Column(name = "product_review")
    private String productReview;

    @Column(name = "product_ratings")
    private int productRatings;

    public CatalogueReviews(){

    }

    public CatalogueReviews(int reviewId, int productRatings, String productReview) {
    this.reviewId = reviewId;
    this.productRatings = productRatings;
    this.productReview = productReview;
    }
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getProductReview() {
        return productReview;
    }

    public void setProductReview(String productReview) {
        this.productReview = productReview;
    }

    public int getProductRatings() {
        return productRatings;
    }

    public void setProductRatings(int productRatings) {
        this.productRatings = productRatings;
    }

	public Catalogue getCatalogue() {
		return catalogue;
	}

	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}



}
