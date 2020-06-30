package com.b12.offer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.b12.offer.constant.AllConstant;
import com.b12.offer.dto.OfferResponse;
import com.b12.offer.entity.Offer;
import com.b12.offer.service.OfferService;

@RestController
public class OfferController {

	@Autowired
	private OfferService offerService;

	@GetMapping(value ="/{offerId}",produces = "application/xml")
	public ResponseEntity<OfferResponse> getOffer(@PathVariable String offerId){
		OfferResponse offerResponse = new OfferResponse();
		ResponseEntity<OfferResponse> response = null;
		if( offerId != null && offerId.length() >0 && offerId.matches("//d"))
		{
			Offer offer = offerService.getOffer(Long.parseLong(offerId));
			offerResponse.setResult(true);
			offerResponse.setOfferCategory(offer.getOfferCategory());
			offerResponse.setOfferId(offer.getOfferId());
			offerResponse.setOfferPrice(offer.getOfferPrice());
			offerResponse.setValidFrom(offer.getValidFrom());
			offerResponse.setValidUpto(offer.getValidUpto());
			response = new ResponseEntity<OfferResponse>(offerResponse, HttpStatus.OK);
		}else {
			offerResponse.setMessage(AllConstant.INVALID_OFFER_ID);
			offerResponse.setResult(false);
			response =  new ResponseEntity<OfferResponse>(offerResponse, HttpStatus.BAD_REQUEST);
		}
		return response;
		
	}
}
