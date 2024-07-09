const promise = {
	fetch: async function(url_v, data_v, method) {
		return await fetch(url_v, {
			method: method,
			headers: {
				"Content-Type": "application/json",
				"token" : $("#header_token").val()
			},
			body: JSON.stringify(data_v),
		});
	}
}


window.onload = function() {
	$("button").on("click", async function(ev) {
		let evo = $(ev.currentTarget);
		
		let url_v = evo.siblings("span").text();
		let param_v = {};
		
		param_o = evo.closest(".card").find(".parameter")
		
		let length = param_o.find("div input").length;
		let label_o =  $(param_o.find("label"));
	
		for(let i=0; i<length; i++) {
			let label_v = $(label_o[i]).text();
			let value_v = param_o.find("input")[i].value;
			
			param_v[label_v] = value_v;
		}
		console.log(param_v);
		
		const response = await promise.fetch(url_v, param_v, "POST");
		const result = await response.json();
		
		$(param_o.next()).find(".result pre").html(JSON.stringify(result, null, 2));
	});
}

