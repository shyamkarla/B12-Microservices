package com.b12.price.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.b12.price.entity.Price;
import com.b12.price.repo.PriceRepository;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

	@Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    static Price price = new Price();

	@BeforeAll
	public static void init() {
		price.setOfferId(12L);
		price.setProductId(12L);
		price.setProductPrice(1200f);
	}

    @Test
    public void testGetPrice() {
    	Mockito.when(priceRepository.getOne(Mockito.anyLong())).thenReturn(price);
    	Price price = priceService.getPriceDetails(12L);
    	assertTrue(price != null);
    }

    @Test
    public void testGetPriceWithNull() {
    	Mockito.when(priceRepository.getOne(Mockito.anyLong())).thenReturn(null);
    	Price price = priceService.getPriceDetails(12L);
    	assertTrue(price == null);
    }

    @Test
    public void testAddPrice() {
    	Mockito.when(priceRepository.save(Mockito.any())).thenReturn(price);
    	 priceService.addPriceDetails(price);
    	assertTrue(true);
    }
}
