package com.b12.offer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b12.offer.entity.Offer;
import com.b12.offer.repo.OfferRepository;

@Service
public class OfferService {

	@Autowired
	private OfferRepository offerRepository;

	public Offer getOffer(long offerId) {
		return offerRepository.getOne(offerId);
	}

	public Offer addOffer(Offer offer) {
		return offerRepository.save(offer);
	}

	public List<Offer> getAllOffer() {
		return offerRepository.findAll();
	}

	public Offer updateOffer(Offer offer) {
		return offerRepository.save(offer);
		
	}
}
