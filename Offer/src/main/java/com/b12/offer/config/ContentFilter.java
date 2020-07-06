package com.b12.offer.config;

import com.b12.offer.entity.Offer;

public class ContentFilter {

	@Override
	public boolean equals(Object obj) {
		 if (obj == null || !(obj instanceof Offer)) {
	          return false;
	      }
	      
		 Offer offer = (Offer) obj;
		 return offer.getOfferId() > 0; 
	}
}
