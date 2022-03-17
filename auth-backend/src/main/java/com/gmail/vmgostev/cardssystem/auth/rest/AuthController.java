package com.gmail.vmgostev.cardssystem.auth.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.vmgostev.cardssystem.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService service) {
		this.authService = service;
	}

	@PostMapping("/register")
	public RegisterResponse register(@RequestBody RegisterRequest request) {
		
		System.out.println("Got a register request: " + request.name());
		
		//TODO: Add captcha
		
		try {
			authService.createAccount(request.name(),request.email(), request.pw(), true);
		
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
			
			OAuthLoginResult result = authService.loginWithOAuth(request.token(), request.provider());
			
			return new OAuthResponse(result.status().name(), result.errorMessage());
		}
		catch (Throwable ex) {
			return new OAuthResponse(OAuthLoginResult.OAuthLoginResultStatus.error.name(), "Internal server error");
		}
	}
}
