package com.gmail.vmgostev.cardssystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@PostMapping("/register")
	public RegisterResponse register(@RequestBody RegisterRequest request) {
		
		System.out.println("Got a register request: " + request.name());
		return new RegisterResponse(true, "You want to register " + request.name());
	}
	
	@GetMapping("/status")
	public RegisterResponse status() {
		
		return new RegisterResponse(false, "We're alive");
	}
}
