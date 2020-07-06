package com.b12.offer.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.b12.offer.constant.AllConstant;
import com.b12.offer.dto.OfferResponse;

@ControllerAdvice
public class OfferControllerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(Exception e){
		OfferResponse response = new OfferResponse();
		response.setResult(false);
		response.setMessage(AllConstant.OFFER_ID_NOT_FOUND);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>("{\"message\": \"Offer ID not found in DB\"}",headers,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<OfferResponse> handleException(Exception e){
		OfferResponse response = new OfferResponse();
		response.setResult(false);
		response.setMessage(AllConstant.INTERNAL_SERVER_ERROR+":"+e.getMessage());
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    return new ResponseEntity<OfferResponse>(response,headers,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
