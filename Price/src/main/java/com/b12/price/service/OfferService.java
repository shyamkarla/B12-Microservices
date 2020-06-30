package com.b12.price.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.b12.price.dto.OfferResponse;

@Service
public class OfferService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${offer.url}")
	private String offerURL;

	public OfferResponse getOfferDetails(long offerId) {
		return restTemplate.getForObject(offerURL+offerId, OfferResponse.class);
	}
}
