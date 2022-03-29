package com.gmail.vmgostev.cardssystem.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gmail.vmgostev.cardssystem.auth.AuthException;
import com.gmail.vmgostev.cardssystem.auth.AuthService;
import com.gmail.vmgostev.cardssystem.auth.OAuthDispatcher;
import com.gmail.vmgostev.cardssystem.auth.rest.OAuthGetIdentityResult;

@Component
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	OAuthDispatcher oAuthDispatcher;
	
	@Override
	public long createAccount(String name, String email, String password, boolean sendActivationEmail) {
		
		throw new AuthException("Oh no");
	}
	
	@Override
	public OAuthGetIdentityResult getIdentityWithOAuth(String token, String provider) {
		
		return oAuthDispatcher.getProvider(provider).getIdentity(token);
	}

}
