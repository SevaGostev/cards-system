package com.gmail.vmgostev.cardssystem.auth;

import com.gmail.vmgostev.cardssystem.auth.model.AccountInfo;
import com.gmail.vmgostev.cardssystem.auth.rest.OAuthLoginResult;

public interface AuthService {

	long createAccount(String name, String email, String password, boolean sendActivationEmail) throws AuthException;
	
	long createAccountWithProvider(String name, String email, String providerName, boolean sendActivationEmail) throws AuthException;
	
	void sendActivationKey(long id) throws AuthException;
	
	void activateAccount(long id) throws AuthException;
	
	void activateAccount(String email, String activationKey) throws AuthException;
	
	boolean checkCredentials(String email, String password) throws AuthException;
	
	boolean checkProviderCredentials(String email, String providerName, String token) throws AuthException;
	
	AccountInfo getAccountInfo(long id) throws AuthException;
	
	AccountInfo getAccountInfo(String email) throws AuthException;

	OAuthLoginResult loginWithOAuth(String token, String provider);
	
}
