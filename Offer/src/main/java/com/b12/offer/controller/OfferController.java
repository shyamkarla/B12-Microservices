package com.b12.offer.controller;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b12.offer.constant.AllConstant;
import com.b12.offer.dto.OfferResponse;
import com.b12.offer.entity.Offer;
import com.b12.offer.service.OfferService;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/offers")
@Api(value="offer api", description="To uptain & add the offer details") 
public class OfferController {

	@Autowired
	private OfferService offerService;

	@ApiOperation(value = "get a offer details for a product", response = OfferResponse.class, tags = "GetOfferDetails")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully sending Offer details"),
	        @ApiResponse(code = 400, message = "Invalid Request"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping(value ="/{offerId}",produces = "application/json")
	public ResponseEntity<OfferResponse> getOffer(@PathVariable Long offerId){
		OfferResponse offerResponse = new OfferResponse();
		ResponseEntity<OfferResponse> response = null;
		if( offerId != null && offerId>0)
		{
			Offer offer = offerService.getOffer(offerId);
			offerResponse.setResult(true);
			offerResponse.setOfferCategory(offer.getOfferCategory());
			offerResponse.setOfferId(offer.getOfferId());
			offerResponse.setOfferPercentage(offer.getOfferPercentage());
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

	@ApiOperation(value = "save a offer details for a product", response = OfferResponse.class, tags = "AddOfferDetails")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully added the Offer details"),
	        @ApiResponse(code = 400, message = "Invalid Request"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value = "/",produces = "application/json")
	public ResponseEntity<OfferResponse> addOffer(@RequestBody Offer offer){
		offerService.addOffer(offer);
		OfferResponse response =new OfferResponse();
		response.setMessage(AllConstant.ADDED_SUCCESSFULLY);
		return new ResponseEntity<OfferResponse>(response, HttpStatus.OK);
		
	}

	@ApiOperation(value = "Get all offer details for a product", response = JSONArray.class, tags = "GetAllOfferDetails")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully Sending all offer details"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value = "/",produces = "application/json")
	public ResponseEntity<String> getAllOffer(){
		List<Offer> allOffer = offerService.getAllOffer();
		 Gson gson = new Gson();
		 String jsonOffers = gson.toJson(allOffer);
		return new ResponseEntity<String>("{\"offers\":"+jsonOffers+"}",HttpStatus.OK);		
	}

	@ApiOperation(value = "modify a offer details for a product", response = OfferResponse.class, tags = "UpdateOfferDetails")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully modified Offer details"),
	        @ApiResponse(code = 400, message = "Invalid Request"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})	
	@PutMapping(value ="/",produces = "application/json",consumes = "application/json")
	public ResponseEntity<OfferResponse> updateOfferDetails(@RequestBody Offer offer){
		OfferResponse offerResponse = new OfferResponse();
		ResponseEntity<OfferResponse> response = null;
		if( offer != null && offer.getOfferId() > 0)
		{
			Offer offerReturned = offerService.getOffer(offer.getOfferId());
			offerReturned.setOfferCategory( offer.getOfferCategory() != null ? offer.getOfferCategory() : offerReturned.getOfferCategory());
			offerReturned.setValidFrom( offer.getValidFrom() != null ? offer.getValidFrom() : offerReturned.getValidFrom());
			offerReturned.setValidUpto( offer.getValidUpto() != null ? offer.getValidUpto() : offerReturned.getValidUpto());
			offerReturned.setOfferPercentage( offer.getOfferPercentage() != null ? offer.getOfferPercentage() : offerReturned.getOfferPercentage());

			offerService.updateOffer(offerReturned);
			offerResponse.setResult(true);
			offerResponse.setOfferCategory(offerReturned.getOfferCategory());
			offerResponse.setOfferId(offerReturned.getOfferId());
			offerResponse.setOfferPercentage(offerReturned.getOfferPercentage());
			offerResponse.setValidFrom(offerReturned.getValidFrom());
			offerResponse.setValidUpto(offerReturned.getValidUpto());
			offerResponse.setMessage(AllConstant.UPDATED_SUCCESSFULLY);
			response = new ResponseEntity<OfferResponse>(offerResponse, HttpStatus.OK);
		}else {
			offerResponse.setMessage(AllConstant.INVALID_OFFER_ID);
			offerResponse.setResult(false);
			response =  new ResponseEntity<OfferResponse>(offerResponse, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
}
