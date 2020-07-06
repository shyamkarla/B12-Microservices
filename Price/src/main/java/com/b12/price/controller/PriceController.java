package com.b12.price.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b12.price.constant.AllConstant;
import com.b12.price.dto.OfferResponse;
import com.b12.price.dto.PriceDTO;
import com.b12.price.dto.PriceResponse;
import com.b12.price.dto.Response;
import com.b12.price.entity.Price;
import com.b12.price.service.OfferService;
import com.b12.price.service.PriceService;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/price")
@Api(value = "price api", description = "To uptain & add the price details")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@Autowired
	private OfferService offerService;

	@ApiOperation(value = "get a price details for a product", response = PriceResponse.class, tags = "GetProductPrice",produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully calculated the Product Price"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{priceId}", produces = "application/json")
	public ResponseEntity<Object> getProductPrice(@PathVariable Long priceId) {
		ResponseEntity<Object> responseEntity = null;
		Price price = null;
		if (priceId > 0) {
			price = priceService.getPriceDetails(priceId);
		} else {
			return new ResponseEntity<Object>(new Response(AllConstant.INVALID_PRICE_ID), HttpStatus.BAD_REQUEST);
		}
		if (price != null) {
			return calculatePriceWithOffer(price);
		} else {
			responseEntity = new ResponseEntity<Object>(new Response(AllConstant.PRICE_ID_NOT_FOUND),
					HttpStatus.NOT_FOUND);
		}
		return responseEntity;

	}

	private ResponseEntity<Object> calculatePriceWithOffer(Price price) {
		PriceResponse priceResponse = new PriceResponse();
		
			OfferResponse offerResponse;
			try {
				offerResponse = offerService.getOfferDetails(price.getOfferId());
				System.out.println("Received Offer.."+offerResponse);
			} catch (Exception e) {
				System.out.println("Offer Cant apply Now....Offer API Internal Server Error ");
				offerResponse = null;
			}
			if (offerResponse != null && offerResponse.isResult()) {
				if (offerResponse.getValidFrom().isBefore(LocalDate.now())
						&& offerResponse.getValidUpto().isAfter(LocalDate.now())) {
					priceResponse.setDiscountedPrice(price.getProductPrice() - ( (offerResponse.getOfferPercentage() /100 ) *price.getProductPrice()));
					priceResponse.setProductPrice(price.getProductPrice());
					priceResponse
							.setDiscountPercentage(offerResponse.getOfferPercentage());
					priceResponse.setProductId(price.getProductId());
					return new ResponseEntity<Object>(priceResponse, HttpStatus.OK);
				} else {
					System.out.println("Offer Expired....Returning Actual Value");
				}
			} else {
				System.out.println("Offer not found for the product....Returning Actual Value");
			}
			System.out.println(price.getProductId());
		priceResponse.setProductId(price.getProductId());
		priceResponse.setProductPrice(price.getProductPrice());
		priceResponse.setDiscountedPrice(price.getProductPrice());
		priceResponse.setDiscountPercentage(0F);
		return new ResponseEntity<Object>(priceResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "save a price details for a product", response = Price.class,produces = "application/json", consumes = "application/json", tags = "AddProductPriceDetails")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the Product Price details"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addPriceDetails(@RequestBody PriceDTO price) {
		if (price == null) {
			return new ResponseEntity<Object>(new Response(AllConstant.INVALID_PRICE_DETAILS),
					HttpStatus.BAD_REQUEST);
		}
		Price returnedPrice = priceService.addPriceDetails(price);
		return new ResponseEntity<Object>(returnedPrice, HttpStatus.OK);

	}

	@ApiOperation(value = "Get all price details for a product",produces = "application/json", response = List.class, tags = "GetAllPriceDetails")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Sending all price details"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	@GetMapping(produces = "application/json")
	public ResponseEntity<String> getAllPriceDetails() {
		List<Price> allPrice = priceService.getAll();
		 Gson gson = new Gson();
		 String jsonPrices = gson.toJson(allPrice);
		return new ResponseEntity<String>("{\"priceDetails\":" + jsonPrices + "}", HttpStatus.OK);

	}

	@ApiOperation(value = "modify a price details for a product", response = Price.class,produces = "application/json", consumes = "application/json", tags = "UpdatePriceDetails")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully modified Price details"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PutMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateOfferDetails(@RequestBody Price price) {
		if (price != null && price.getOfferId() > 0) {
			
			Price priceReturned = priceService.getPriceDetails(price.getProductId());
			priceReturned.setOfferId(price.getOfferId() != null ? price.getOfferId() : priceReturned.getOfferId());
			priceReturned.setProductPrice(
					price.getProductPrice() != null ? price.getProductPrice() : priceReturned.getProductPrice());

			Price newPrice = priceService.updatePriceDetails(priceReturned);
			return new ResponseEntity<Object>(newPrice, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new Response(AllConstant.INVALID_PRICE_ID), HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "delete a price details for a product", tags = "DeletePriceDetails")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted Price details"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@DeleteMapping(value = "/{priceId}", produces = "application/json")
	public ResponseEntity<Object> deleteOfferDetails(@PathVariable Long priceId) {
		if (priceId > 0) {
			 priceService.deletePriceDetails(priceId);
			 Long offerId = priceService.getPriceDetails(priceId).getOfferId();
			 offerService.deleteOfferDetails(offerId);
			return new ResponseEntity<Object>(new Response(AllConstant.DELETE_MESSAGE), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new Response(AllConstant.INVALID_PRICE_ID), HttpStatus.NOT_FOUND);
	}
}
