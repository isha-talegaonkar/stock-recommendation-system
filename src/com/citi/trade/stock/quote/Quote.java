package com.citi.trade.stock.quote;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Quote {
	
	@JsonProperty("High")
	private String high;
	
	@JsonProperty("Low")
	private String low;
	
	@JsonProperty("Volume")
	private String volume;
	
	@JsonProperty("Symbol")
	private String symbol;
	
	@JsonProperty("Adj_Close")
	private String adj_Close;
	
	@JsonProperty("Close")
	private String close;
	
	@JsonProperty("Date")
	private String date;
	
	@JsonProperty("Open")
	private String open;
	
	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getAdj_Close() {
		return adj_Close;
	}

	public void setAdj_Close(String adj_Close) {
		this.adj_Close = adj_Close;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

}
