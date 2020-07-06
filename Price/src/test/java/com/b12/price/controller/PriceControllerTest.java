package com.b12.price.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.b12.price.dto.OfferResponse;
import com.b12.price.entity.Price;
import com.b12.price.service.OfferService;
import com.b12.price.service.PriceService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PriceController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PriceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PriceService priceService;

	@MockBean
	private OfferService offerService;

	static Price price = new Price();

	static ArrayList<Price> prices = new ArrayList<Price>();

	static OfferResponse offerResponse = new OfferResponse();

	@BeforeAll
	public static void init() {
		price.setOfferId(12L);
		price.setProductId(12L);
		price.setProductPrice(1200f);
		prices.add(price);
		offerResponse.setOfferCategory("Electronics");
		offerResponse.setOfferId(12L);
		offerResponse.setOfferPercentage(20f);
		offerResponse.setResult(true);
		offerResponse.setValidFrom(LocalDate.of(2020, 6, 2));
		offerResponse.setValidUpto(LocalDate.of(2020, 6, 2));
	}

	@Test
	@Order(1)
	public void testGetOfferedPriceWithInvalidProductId() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12e");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"message\":\"Price ID not received / Invalid\"}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	@Order(2)
	public void testGetOfferedPriceWithValidProductId() throws Exception {

		Mockito.when(priceService.getPriceDetails(Mockito.anyLong())).thenReturn(null);

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"message\":\"Price ID not found in DB\"}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}

	@Test
	@Order(3)
	public void testGetOfferedPrice() throws Exception {

		Mockito.when(priceService.getPriceDetails(Mockito.anyLong())).thenReturn(price);

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"productId\":12,\"productPrice\":1200.0,\"discountedPrice\":1200.0,\"discountPercentage\":0.0}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(4)
	public void testGetOfferedPriceWithException() throws Exception {

		Mockito.when(priceService.getPriceDetails(Mockito.anyLong())).thenThrow(RuntimeException.class);

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"message\":\"Exception Occured..\"}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());

	}

	@Test
	@Order(5)
	public void testAddPriceWithException() throws Exception {

		Mockito.when(priceService.addPriceDetails((Mockito.any()))).thenThrow(EntityNotFoundException.class);

		RequestBuilder request = MockMvcRequestBuilders.post("/price").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"productPrice\":1200.0,\"offerId\":12,\"discountPercentage\":25.0}");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"message\":\"Price ID not found in DB\"}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}

	@Test
	@Order(6)
	public void testAddPrice() throws Exception {

		Mockito.when(priceService.addPriceDetails((Mockito.any()))).thenReturn(price);

		RequestBuilder request = MockMvcRequestBuilders.post("/price").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("{\"productPrice\":1200.0,\"offerId\":12,\"discountPercentage\":25.0}");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"productId\":12,\"offerId\":12,\"productPrice\":1200.0}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(7)
	public void testGetAllPrice() throws Exception {

		Mockito.when(priceService.getAll()).thenReturn(prices);

		RequestBuilder request = MockMvcRequestBuilders.get("/price").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"priceDetails\":[{\"productId\":12,\"offerId\":12,\"productPrice\":1200.0}]}";
		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(8)
	public void testGetOfferedPriceWithOfferExpired() throws Exception {

		Mockito.when(priceService.getPriceDetails(Mockito.anyLong())).thenReturn(price);

		Mockito.when(offerService.getOfferDetails(Mockito.anyLong())).thenReturn(offerResponse);

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"productId\":12,\"productPrice\":1200.0,\"discountedPrice\":1200.0,\"discountPercentage\":0.0}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(9)
	public void testGetOfferedPriceWithOfferZero() throws Exception {

		Mockito.when(priceService.getPriceDetails(Mockito.anyLong())).thenReturn(price);
		offerResponse.setOfferPercentage(0);
		offerResponse.setValidUpto(LocalDate.of(2022, 1, 1));
		Mockito.when(offerService.getOfferDetails(Mockito.anyLong())).thenReturn(offerResponse);

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"productId\":12,\"productPrice\":1200.0,\"discountedPrice\":1200.0,\"discountPercentage\":0.0}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(10)
	public void testGetOfferedPriceWithOfferNull() throws Exception {

		Mockito.when(priceService.getPriceDetails(Mockito.anyLong())).thenReturn(price);
		Mockito.when(offerService.getOfferDetails(Mockito.anyLong())).thenReturn(null);

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"productId\":12,\"productPrice\":1200.0,\"discountedPrice\":1200.0,\"discountPercentage\":0.0}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(11)
	public void testGetOfferedPriceWithOffer() throws Exception {

		Mockito.when(priceService.getPriceDetails(Mockito.anyLong())).thenReturn(price);
		offerResponse.setOfferPercentage(50);
		offerResponse.setValidUpto(LocalDate.of(2022, 1, 1));
		Mockito.when(offerService.getOfferDetails(Mockito.anyLong())).thenReturn(offerResponse);

		RequestBuilder request = MockMvcRequestBuilders.get("/price/12");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"productId\":12,\"productPrice\":1200.0,\"discountedPrice\":600.0,\"discountPercentage\":50.0}";

		assertEquals(expectedResult, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
}
