package com.gmail.vmgostev.cardssystem.auth.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.vmgostev.cardssystem.account.AccountInfo;
import com.gmail.vmgostev.cardssystem.auth.AuthService;
import com.gmail.vmgostev.cardssystem.session.SessionData;
import com.gmail.vmgostev.cardssystem.session.SessionService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	private final SessionService sessionService;
	
	public AuthController(AuthService authService, SessionService sessionService) {
		this.authService = authService;
		this.sessionService = sessionService;
	}

	@PostMapping("/register")
	public RegisterResponse register(@RequestBody RegisterRequest request) {
		
		System.out.println("Got a register request: " + request.name());
		
		//TODO: Add captcha
		
		try {
			long accountID = authService.createAccount(request.name(),request.email(), true);
			authService.addPasswordLoginMethod(accountID, request.pw());
		
			return new RegisterResponse(true, "You want to register " + request.name());
		}
		catch(Throwable ex) {
			return new RegisterResponse(false, "Internal server error");
		}
	}
	
	@GetMapping("/status")
	public RegisterResponse status() {
		
		return new RegisterResponse(false, "We're alive");
	}
	
	@PostMapping("/oauth")
	public OAuthResponse loginWithOAuth(@RequestBody OAuthRequest request) {
		
		try {
			
			OAuthGetIdentityResult identity = authService.getIdentityWithOAuth(request.token(), request.provider());
			
			if(!identity.success()) {
				System.err.println(identity.errorMessage());
				return new OAuthResponse("error", null, "Could not identify user with provider");
			}
			else {
				AccountInfo account = authService.getAccountInfo(request.provider(), identity.providerSpecificID());
				
				if(account == null) {
					
					//We don't have an account associated with this OAuth provider account.
					
					if(identity.email() == null) {
						
						//Provider hasn't given us the user's email address, need to ask for it explicitly.
						
						String sessionID = sessionService.startAnonymousSession(0L);
						sessionService.setStoredValue(sessionID, "oauthProvider", request.provider());
						sessionService.setStoredValue(sessionID, "oauthProviderSpecificID", identity.providerSpecificID());
						return new OAuthResponse("needEmail", sessionID, "");
					}
					else {
						
						//We have the user's email, can immediately create account and send confirmation email.
						
						if(authService.getAccountInfo(identity.email()) != null) {
							//We already have an account for this email address, so the user must add this OAuth provider
							//as a login method first. We won't log in here.
							
							return new OAuthResponse("emailTaken", null, "");
						}
						else {
							long id = authService.createAccount(identity.name() == null ? "" : identity.name(), identity.email(), true);
							authService.addOAuthProviderLoginMethod(id, request.provider(), identity.providerSpecificID());
							String sessionID = sessionService.startSession(id, 0L);
							return new OAuthResponse("createdAccount", sessionID, "");
						}
					}
				}
				else {
					//We have an account, can log in.
					String sessionID = authService.login(account.id());
					return new OAuthResponse("loggedIn", sessionID, "");
				}
			}
		}
		catch (Throwable ex) {
			return new OAuthResponse("error", null, "Internal server error");
		}
	}
}
