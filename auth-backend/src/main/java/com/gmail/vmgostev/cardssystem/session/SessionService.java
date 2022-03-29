package com.gmail.vmgostev.cardssystem.session;

public interface SessionService {

	long identify(String sessionID);
	
	String startAnonymousSession(long expiresIn);
	
	String startSession(long userID, long expiresIn);
	
	void refreshSession(String sessionID, long expiresIn);
	
	void stopSession(String sessionID);
	
	Object getStoredValue(String sessionID, String key);
	
	void setStoredValue(String sessionID, String key, Object value);
}
