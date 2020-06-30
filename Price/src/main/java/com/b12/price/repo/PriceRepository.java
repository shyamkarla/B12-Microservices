package com.b12.price.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b12.price.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long>{

}
