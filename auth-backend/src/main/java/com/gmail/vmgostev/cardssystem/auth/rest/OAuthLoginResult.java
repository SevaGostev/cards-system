package com.gmail.vmgostev.cardssystem.auth.rest;

public record OAuthLoginResult(OAuthLoginResultStatus status, String errorMessage) {
	
	public enum OAuthLoginResultStatus {
		createdAccount,
		needEmail,
		loggedIn,
		error
	}
}
