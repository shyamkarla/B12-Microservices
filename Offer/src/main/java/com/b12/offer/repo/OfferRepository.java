package com.b12.offer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b12.offer.entity.Offer;
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
