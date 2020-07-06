package com.b12.price.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b12.price.dto.PriceDTO;
import com.b12.price.entity.Price;
import com.b12.price.repo.PriceRepository;

@Service
public class PriceService {

	@Autowired
	private PriceRepository priceRepository;

	public Price getPriceDetails(Long priceId) {
			return priceRepository.getOne(priceId);
	}

	public Price addPriceDetails(PriceDTO priceDto) {
		Price price = new Price();
		price.setOfferId(priceDto.getOfferId());
		price.setProductPrice(priceDto.getProductPrice());
		return priceRepository.save(price);
	}

	public List<Price> getAll() {
		return priceRepository.findAll();
	}

	public Price updatePriceDetails(Price price) {
		return priceRepository.save(price);
	}

	public void deletePriceDetails(Long priceId) {
		priceRepository.deleteById(priceId);
	}
}
