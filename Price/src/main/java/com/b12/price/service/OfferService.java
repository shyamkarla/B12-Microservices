package com.b12.price.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.b12.price.dto.OfferResponse;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class OfferService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	EurekaClient eurekaClient;

	private final String offerServiceName = "offer-service";

	public OfferResponse getOfferDetails(long offerId) {
		InstanceInfo info = eurekaClient.getNextServerFromEureka(offerServiceName, false);

		String baseURL = info.getHomePageUrl();
		baseURL = baseURL + "/" + offerId;
		System.out.println("BaseURL ---" + baseURL);
		return restTemplate.getForObject(baseURL, OfferResponse.class);
	}

	public void deleteOfferDetails(long offerId) {
		InstanceInfo info = eurekaClient.getNextServerFromEureka(offerServiceName, false);
		String baseURL = info.getHomePageUrl();
		baseURL = baseURL + "/" + offerId;
		System.out.println("BaseURL ---" + baseURL);
		restTemplate.delete(baseURL);
	}
}
