package com.citi.trade.stock.quote;

public class Query {
	private int count;
	private String created;
	private String lang;
	private Results results;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String createdDate) {
		this.created = createdDate;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String language) {
		this.lang = language;
	}
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	

}
