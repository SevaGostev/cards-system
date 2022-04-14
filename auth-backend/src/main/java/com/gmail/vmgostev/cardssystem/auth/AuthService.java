package com.gmail.vmgostev.cardssystem.auth;

import com.gmail.vmgostev.cardssystem.account.AccountInfo;
import com.gmail.vmgostev.cardssystem.auth.rest.OAuthGetIdentityResult;

public interface AuthService {

	long createAccount(String name, String email, boolean sendActivationEmail) throws AuthException;
	
	void addPasswordLoginMethod(long id, String password) throws AuthException;
	
	void addOAuthProviderLoginMethod(long id, String providerName, String providerSpecificID) throws AuthException;
	
	void sendActivationKey(long id) throws AuthException;
	
	void activateAccount(long id) throws AuthException;
	
	void activateAccount(String email, String activationKey) throws AuthException;
	
	boolean checkCredentials(String email, String password) throws AuthException;
	
	boolean checkProviderCredentials(String email, String providerName, String token) throws AuthException;
	
	AccountInfo getAccountInfo(long id) throws AuthException;
	
	AccountInfo getAccountInfo(String email) throws AuthException;
	
	AccountInfo getAccountInfo(String providerName, String providerSpecificID) throws AuthException;

	OAuthGetIdentityResult getIdentityWithOAuth(String token, String providerName);
	
	String login(long id);
}
