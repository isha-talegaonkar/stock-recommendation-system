package com.citi.trade.stock.quote;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Results {
	
	@JsonProperty("quote")
	private List<Quote> quote;
	
	public List<Quote> getQuote() {
		return quote;
	}

	public void setQuote(List<Quote> quote) {
		this.quote = quote;
	}

}
