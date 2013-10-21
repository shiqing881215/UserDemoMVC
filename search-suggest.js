function searchSuggest(invoker) {
	debugger;

	// before open, which means the ready state
	if (request.readyState != 0) return;
	
	var username = document.getElementById("username").value;
	
	// If username is empty, clear the suggest div
	if (username == "") {
		clearSuggestedResults("search_suggest");
		return;
	}
	
	var url = "search-ajax.do?" + "username=" + escape(username);
	
	// Callback ---- get the result back from server
	// This means each time when the request state is changed, suggestUpdate method is called.
	// State : 0 : request not initialized
	//		   1 : server connection setup
	//         2 : request received
	//         3 : request processed
	//         4 : request completed and response ready
	// ATTENTION HERE!!!!!!!!!!!!!!!!!!!!!!
	//     MUST ASSIGN A FUNCTION TO THIS!!! like (1) function() {.....}  (2) suggestUpdate    DON'T USE suggestUpdate("ddd") independently
	request.onreadystatechange = function() {suggestUpdate("username");}
	
	// Send the search suggest request to the suggest servlet to do the real match
	request.open("GET", url, true);  // This line will cause the request state change to 1
	request.send(null);
}

// Clear suggest result by removing all children of search children
function clearSuggestedResults(elementId) {
	var divSearchSuggest = document.getElementById(elementId);
	while (divSearchSuggest.hasChildNodes()) {
		divSearchSuggest.removeChild(divSearchSuggest.firstChild);
	}
}

/**
 * Check the state whether equals to 4
 * then use the request's responseXML to create the new dom element to display the result
 * @param elementId
 */
function suggestUpdate(elementId) { 
	debugger;
	// 4 --- response received
	if (request.readyState != 4) return;
	
	if (request.status != 200) {
		alert("Error, request status is " + request.status);
		return;
	}
	
	clearSuggestedResults("search_suggest");
	
	// Get response xml file
	var xmlDoc = request.responseXML;
	// Get all result elements
	var results = xmlDoc.getElementsByTagName("result");
	
	var suggestDivEl = document.getElementById("search_suggest");
	
	// Get value from the xml file by tag
	// Add each result to the suggested div node
	for (var i = 0; i < results.length && i < 20; i++) {
		//var id = results[i].getElementByTagName("id")[0].firstChild.nodeValue;
		var username = results[i].getElementsByTagName("username")[0].firstChild.nodeValue;
		
		var suggestLineEL = createSuggestLine(username);
		
		suggestDivEl.appendChild(suggestLineEL);
	}
	
	// After handle this one, create a new request
	request = createRequest();
}

// Create one suggest html node wrapped by a div
function createSuggestLine(text) {
	var textEl = document.createTextNode(text);
	var divEl = document.createElement("div");
	divEl.appendChild(textEl);
	
	return divEl;
}



