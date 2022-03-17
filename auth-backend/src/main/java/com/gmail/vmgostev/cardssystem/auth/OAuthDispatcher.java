package com.gmail.vmgostev.cardssystem.auth;

public interface OAuthDispatcher {
	
	OAuthProvider getProvider(String name);
}
