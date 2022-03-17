package com.gmail.vmgostev.cardssystem.auth;

import com.gmail.vmgostev.cardssystem.auth.rest.OAuthLoginResult;

public interface OAuthProvider {

	OAuthLoginResult login(String token);
}
