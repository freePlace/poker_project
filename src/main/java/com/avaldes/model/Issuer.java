package com.avaldes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Issuer {
	@Id
	private String id;
	private String ticker;
	
	private String issuerName;
	private String issuerType;
	private String country;
	
	public Issuer() {
	}
	
	public Issuer(String ticker, String issuerName, String issuerType, String country) {
		setTicker(ticker);
		setIssuerName(issuerName);
		setIssuerType(issuerType);
		setCountry(country);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTicker() {
		return ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public String getIssuerName() {
		return issuerName;
	}
	
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	
	public String getIssuerType() {
		return issuerType;
	}
	
	public void setIssuerType(String issuerType) {
		this.issuerType = issuerType;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString() {
		return "[" + getTicker() 
				+ ", " + getIssuerName()
				+ ", " + getIssuerType()
				+ ", " + getCountry()
				+ "]";
	}
}
