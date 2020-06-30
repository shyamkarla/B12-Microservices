package com.b12.price.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.b12.price.constant.AllConstant;
import com.b12.price.dto.Response;

@ControllerAdvice
public class PriceControllerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Response> handleEntityNotFoundException(Exception e){
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    return new ResponseEntity<Response>(new Response(AllConstant.PRICE_ID_NOT_FOUND),headers,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception e){
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    String message = "Exception Occured..";
	    if(e.getMessage() != null) {
	    	message = message + e.getMessage();
	    }
	    System.out.println(message);
	    return new ResponseEntity<Response>(new Response(message),headers,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
