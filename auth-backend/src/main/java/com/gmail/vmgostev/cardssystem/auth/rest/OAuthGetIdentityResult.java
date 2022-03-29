package com.gmail.vmgostev.cardssystem.auth.rest;

public record OAuthGetIdentityResult(
		String providerName,
		String providerSpecificID,
		String name,
		String email,
		boolean success,
		String errorMessage) {

}
