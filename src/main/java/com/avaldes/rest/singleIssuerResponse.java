package com.avaldes.rest;

import com.avaldes.model.Issuer;

public class singleIssuerResponse {
	private boolean success;
	private Issuer issuer;
	
	public singleIssuerResponse(boolean success, Issuer issuer) {
		this.success = success;
		this.issuer = issuer;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Issuer getIssuers() {
		return issuer;
	}
	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}
}
