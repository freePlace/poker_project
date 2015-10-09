package com.avaldes.rest;

import java.util.List;

import com.avaldes.model.Issuer;

public class multipleIssuerResponse {
	private boolean success;
	private List<Issuer> issuers;
	
	public multipleIssuerResponse(boolean success, List<Issuer> issuers) {
		this.success = success;
		this.issuers = issuers;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<Issuer> getIssuers() {
		return issuers;
	}
	public void setIssuers(List<Issuer> issuers) {
		this.issuers = issuers;
	}
}
