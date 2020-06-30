package com.b12.price.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b12.price.constant.AllConstant;
import com.b12.price.dto.OfferResponse;
import com.b12.price.dto.PriceResponse;
import com.b12.price.dto.Response;
import com.b12.price.entity.Price;
import com.b12.price.service.OfferService;
import com.b12.price.service.PriceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/price")
@Api(value="price api", description="To uptain & add the price details") 
public class PriceController {

	@Autowired
	private PriceService priceService;

	@Autowired
	private OfferService offerService;

	@ApiOperation(value = "get a price details for a product", response = PriceResponse.class, tags = "GetProductPrice")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully calculated the Product Price"),
	        @ApiResponse(code = 400, message = "Invalid Request"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping(value = "/{priceIdStr}",produces = "application/json")
	public ResponseEntity<Object> getProductPrice(@PathVariable String priceIdStr) {
		ResponseEntity<Object> responseEntity = null;
		Price price = null;
		if (priceIdStr != null && priceIdStr.length() >0 && priceIdStr.matches("\\d+")) {
			price = priceService.getPriceDetails(Long.parseLong(priceIdStr));
		} else {
			return new ResponseEntity<Object>(new Response(AllConstant.INVALID_PRICE_ID), HttpStatus.BAD_REQUEST);
		}
		if (price != null) {
			return calculatePriceWithOffer(price);
		}else {
			responseEntity = new ResponseEntity<Object>(new Response(AllConstant.PRICE_ID_NOT_FOUND), HttpStatus.NOT_FOUND);
		}
		return responseEntity;

	}

	private ResponseEntity<Object> calculatePriceWithOffer(Price price) {
		PriceResponse priceResponse = new PriceResponse();
		
		if(String.valueOf(12).length() <1) {
			OfferResponse offerResponse = offerService.getOfferDetails(price.getOfferId());
			if(offerResponse != null && offerResponse.isResult()) {
			if (offerResponse.getValidFrom().isBefore(LocalDate.now()) && offerResponse.getValidUpto().isAfter(LocalDate.now())) {
				priceResponse.setDiscountedPrice(offerResponse.getOfferPrice());
				priceResponse.setProductPrice(price.getProductPrice());
				priceResponse.setDiscountPercentage(priceResponse.getDiscountedPrice()*100/price.getProductPrice());
				return new ResponseEntity<Object>(priceResponse, HttpStatus.ACCEPTED);
			}else {
				System.out.println("Offer Expired....Returning Actual Value");
			}
			}else {
				System.out.println("Offer not found for the product....Returning Actual Value");
			}
			
		}
		priceResponse.setProductPrice(price.getProductPrice());
		priceResponse.setDiscountedPrice(price.getProductPrice() - (price.getDiscountPercentage()/100)*price.getProductPrice());
		priceResponse.setDiscountPercentage(price.getDiscountPercentage());
		return new ResponseEntity<Object>(priceResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "save a price details for a product", response = PriceResponse.class, tags = "AddProductPriceDetails")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully added the Product Price details"),
	        @ApiResponse(code = 400, message = "Invalid Request"),
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(consumes = "application/json",produces = "application/json")
	public ResponseEntity<Response> addPriceDetails(@RequestBody Price price) {
		if(price == null) {
			return new ResponseEntity<Response>(new Response(AllConstant.INVALID_PRICE_DETAILS), HttpStatus.BAD_REQUEST);
		}
		priceService.addPriceDetails(price);
		return new ResponseEntity<Response>(new Response(AllConstant.SUCCESS_RESPONSE),HttpStatus.OK);
		
	}
	
}
