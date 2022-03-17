const authenticateEndpoint = '/auth/oauth';

const welcomeURL = '/welcome.html';
const enterEmailURL = '/auth/enterEmail.html'

function sendLoginRequest() {
	const params = new URLSearchParams(window.location.search);
	
	const token = params.get("code");
	const state = new URLSearchParams(params.get("state"));
	alert(state);
	const stateParams = new URLSearchParams(state);
	
	if(token != null) {
		
		const data = new Object();
		data.token = token;
		data.provider = stateParams.get("provider");
		
		const response = send(data, authenticateEndpoint);
		
		switch(response.status) {
			case "error":
				showErrorMessage(response.errorMessage ? response.errorMessage : "Unknown error");
				break;
			case "createdAccount":
			case "loggedIn":
				window.location = welcomeURL;
				break;
			case "needEmail":
				window.location = enterEmailURL;
				break;
			default:
				showErrorMessage("Unknown error");
		}
	}
}

async function send(data, destination)
{
	const str = JSON.stringify(data);
	
	const response = await fetch(destination, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: str
		});
	const resjson = await response.json();
	
	alert(resjson.message);
	return resjson;
}

window.onload = sendLoginRequest();