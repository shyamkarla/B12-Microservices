package com.b12.offer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.b12.offer.entity.Offer;
import com.b12.offer.service.OfferService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OfferController.class)
@TestMethodOrder(OrderAnnotation.class)
public class OfferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OfferService offService;

	static Offer offer = new Offer();

	static ArrayList<Offer> offers = new ArrayList<Offer>();

	@BeforeAll
	public static void init() {
		offer.setOfferCategory("Electronices");
		offer.setOfferId(2L);
		offer.setOfferPercentage(20F);
		offer.setValidFrom(LocalDate.now());
		offer.setValidUpto(LocalDate.now());
		offers.add(offer);

	}

	@Test
	@Order(1)
	public void testGetOffereWithOfferId() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/offers/0");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "\"message\":\"Offer ID not received / Invalid\"";

		assertTrue(response.getContentAsString().contains(expectedResult));
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	@Order(2)
	public void testGetOffereWithOfferIdWithNull() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/offers/2");
		Mockito.when(offService.getOffer((Mockito.anyLong()))).thenReturn(null);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "\"message\":\"Internal Server Error:null\"";

		assertTrue(response.getContentAsString().contains(expectedResult));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());

	}

	@Test
	@Order(3)
	public void testGetOffereWithOfferIdValid() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/offers/2");
		Mockito.when(offService.getOffer((Mockito.anyLong()))).thenReturn(offer);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"offerId\":2,\"offerPercentage\":20.0,\"offerCategory\":\"Electronices\",\"validUpto\":\"2020-07-03\",\"result\":true,\"validFrom\":\"2020-07-03\"}";

		assertTrue(response.getContentAsString().contains(expectedResult));
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(4)
	public void testGetAllOffere() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/offers/");
		Mockito.when(offService.getAllOffer()).thenReturn(offers);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"offers\":[{\"offerId\":2,\"offerPercentage\":20.0,\"offerCategory\":\"Electronices\",\"validUpto\":{\"year\":2020,\"month\":7,\"day\":3},\"ValidFrom\":{\"year\":2020,\"month\":7,\"day\":3}}]}";

		assertEquals(expectedResult,response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(5)
	public void testGetAllOffereWithEmpty() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/offers/");
		Mockito.when(offService.getAllOffer()).thenReturn(new ArrayList<Offer>());

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "{\"offers\":[]}";

		assertEquals(expectedResult,response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(6)
	public void testAddOffer() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.post("/offers/").contentType(MediaType.APPLICATION_JSON).content("{\"offerPercentage\":20.0,\"offerCategory\":\"Electronices\",\"validUpto\":\"2020-07-02\",\"result\":true,\"validFrom\":\"2020-07-02\"}");
		Mockito.when(offService.addOffer(Mockito.any())).thenReturn(offer);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertTrue(response.getContentAsString().contains("\"message\":\"Offer added successfully\""));
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@Order(7)
	public void testUpdateOfferWithError() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.put("/offers/").contentType(MediaType.APPLICATION_JSON).content("{\"offerPercentage\":20.0,\"offerCategory\":\"Electronices\",\"validUpto\":\"2020-07-02\",\"result\":true,\"validFrom\":\"2020-07-02\"}");
		Mockito.when(offService.updateOffer(Mockito.any())).thenThrow(EntityNotFoundException.class);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "\"message\":\"Offer ID not received / Invalid\"";

		assertTrue(response.getContentAsString().contains(expectedResult));
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	@Order(7)
	public void testUpdateOffer() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.put("/offers/").contentType(MediaType.APPLICATION_JSON).content("{\"offerPercentage\":20.0,\"offerCategory\":\"Electronices\",\"validUpto\":\"2020-07-02\",\"result\":true,\"validFrom\":\"2020-07-02\"}");
		Mockito.when(offService.updateOffer(Mockito.any())).thenReturn(offer);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expectedResult = "\"message\":\"Offer ID not received / Invalid\"";

		assertTrue(response.getContentAsString().contains(expectedResult));
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
}
