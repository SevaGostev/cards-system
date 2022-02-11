const createUserEndpoint = 'http://localhost:8080/auth/status';


function submit()
{
	
	const data = collectData();
	
	if(!validateData(data))
		return false;
	
	
	send(data, createUserEndpoint);
	
	return true;
}

async function send(data, destination)
{
	//const str = JSON.stringify(data);
	
	//const response = await fetch(destination, {method: 'POST', body: str});
	const response = await fetch(destination, {method: 'GET'});
	const resjson = await response.json();
	
	alert(resjson.message);
}

function collectData()
{
	const data = new Object();
	
	const els = document.getElementById("details").elements;
	
	data.name = els.namedItem("name").value;
	data.email = els.namedItem("email").value;
	data.pw = els.namedItem("pw").value;
	data.confirmpw = els.namedItem("confirmpw").value;
	
	return data;
}

function validateData(data)
{
	var success = true;
	
	const collection = document.getElementById("details").getElementsByClassName("errorLabel");
	
	for(var i = 0; i < collection.length; i ++)
	{
		var itm = collection.item(i);
		itm.hidden = true;
	}
	
	if(data.name.trim() == "")
	{
		document.getElementById("emptyNameError").hidden = false;
		success = false;
	}
	
	if(data.email.trim() == "")
	{
		document.getElementById("emptyEmailError").hidden = false;
		success = false;
	}
	
	if(data.pw.trim() == "")
	{
		document.getElementById("emptyPasswordError").hidden = false;
		success = false;
	}
	
	if(data.pw != data.confirmpw)
	{
		document.getElementById("pwMismatchError").hidden = false;
		success = false;
	}
	
	return success;
}