package com.gmail.vmgostev.cardssystem.auth.impl;

import java.net.URI;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vmgostev.cardssystem.account.AccountInfo;
import com.gmail.vmgostev.cardssystem.auth.AuthService;
import com.gmail.vmgostev.cardssystem.auth.OAuthProvider;
import com.gmail.vmgostev.cardssystem.auth.rest.OAuthGetIdentityResult;

@Component
public class GoogleOAuthProvider implements OAuthProvider {

	//TODO: Get this from the Discovery Document
	private final URI authenticationEndpoint = URI.create("https://oauth2.googleapis.com/token");
	
	private final String providerName = "google";
	
	@Autowired
	private GoogleOAuthConfig config;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public OAuthGetIdentityResult getIdentity(String token) {
		
		String errorMessage = "";
		
		GoogleResponse response = getAccessData(token);
		
		String[] chunks = response.id_token.split("\\.");
		
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String header  = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			JsonNode payloadNode = mapper.readTree(payload);
			
			String googleID = payloadNode.get("sub").asText();
			String email = payloadNode.get("email").asText();
			
			JsonNode nameNode = payloadNode.get("name");
			String name = nameNode != null ? nameNode.asText() : "";
			
			return new OAuthGetIdentityResult(providerName, googleID, name, email, true, "");
			
		}
		catch (JsonMappingException e) {
			errorMessage = "Could not parse ID token";
		}
		catch (JsonProcessingException e) {
			errorMessage = "Could not parse ID token";
		}
		
		return new OAuthGetIdentityResult(null, null, null, null, false, errorMessage);
	}
	
	private GoogleResponse getAccessData(String token) {
		RestTemplate template = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>(5);
		body.add("code", token);
		body.add("client_id", config.getClientID());
		body.add("client_secret", config.getClientSecret());
		body.add("redirect_uri", config.getRedirectURI());
		body.add("grant_type", "authorization_code");
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(body, headers);
		
		return template.postForObject(authenticationEndpoint, entity, GoogleResponse.class);
	}
	
	private static record GoogleResponse(
			String access_token,
			String expires_in,
			String id_token,
			String refresh_token,
			String scope,
			String token_type) {}
	
	public static interface GoogleOAuthConfig {
		
		String getClientID();
		String getClientSecret();
		String getRedirectURI();
	}
}
