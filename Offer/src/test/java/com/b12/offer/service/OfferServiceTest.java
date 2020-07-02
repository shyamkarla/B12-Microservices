package com.b12.offer.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.b12.offer.entity.Offer;
import com.b12.offer.repo.OfferRepository;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

	@Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferService offerService;

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
    public void testGetOffer() {
    	Mockito.when(offerRepository.getOne((Mockito.anyLong()))).thenReturn(offer);
    	Offer Offer = offerService.getOffer(12L);
    	assertTrue(Offer != null);
    }

    @Test
    public void testGetOfferWithNull() {
    	Mockito.when(offerRepository.getOne(Mockito.anyLong())).thenReturn(null);
    	Offer Offer = offerService.getOffer(12L);
    	assertTrue(Offer == null);
    }

    @Test
    public void testAddOffer() {
    	Mockito.when(offerRepository.save(Mockito.any())).thenReturn(offer);
    	 offerService.addOffer(offer);
    	assertTrue(true);
    }

    @Test
    public void testSaveffer() {
    	Mockito.when(offerRepository.save(Mockito.any())).thenReturn(offer);
    	 offerService.updateOffer(offer);
    	assertTrue(true);
    }
}
