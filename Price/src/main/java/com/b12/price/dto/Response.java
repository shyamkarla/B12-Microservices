package com.b12.price.dto;

import lombok.Data;

@Data
public class Response {
	String message;
	public Response(String message){
		this.message = message;
	}
}