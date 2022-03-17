const redirectURL = "https://torre.gostev.me/account/await-login.html";
const googleAuthURL = "https://accounts.google.com/o/oauth2/v2/auth";
const googleClientID = "784718032490-q6b8qokntmjstdgq8ulh0rrvot2nlqmg.apps.googleusercontent.com";
const googleScope = "openid email profile";

function loginGoogle() {
	
	const url = new URL(googleAuthURL);
	const searchParams = new URLSearchParams();
	searchParams.append("client_id", googleClientID);
	searchParams.append("redirect_uri", redirectURL);
	searchParams.append("response_type", "code");
	searchParams.append("scope", googleScope);
	searchParams.append("state", "provider=google");
	
	url.search = searchParams.toString();
	
	window.location.href = url;
}