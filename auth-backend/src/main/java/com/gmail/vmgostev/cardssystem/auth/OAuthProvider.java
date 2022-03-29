package com.gmail.vmgostev.cardssystem.auth;

import com.gmail.vmgostev.cardssystem.auth.rest.OAuthGetIdentityResult;

public interface OAuthProvider {

	OAuthGetIdentityResult getIdentity(String token);
}
