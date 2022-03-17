package com.gmail.vmgostev.cardssystem.auth.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.gmail.vmgostev.cardssystem.auth.AuthException;
import com.gmail.vmgostev.cardssystem.auth.OAuthDispatcher;
import com.gmail.vmgostev.cardssystem.auth.OAuthProvider;

@Component
public class OAuthBeanDispatcher implements OAuthDispatcher {

	@Autowired
	ApplicationContext context;
	
	private Map<String, OAuthProvider> providerMap;
	
	
	public OAuthBeanDispatcher() {
		
		providerMap = context.getBeansOfType(OAuthProvider.class);
	}
	
	@Override
	public OAuthProvider getProvider(String name) {
		OAuthProvider p = providerMap.get(name);
		
		if(p == null)
			throw new AuthException("No matching provider for " + name);
		else
			return p;
	}

}
