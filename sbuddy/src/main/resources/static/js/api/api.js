const promise = {
	fetch: async function(url_v, data_v, method) {
		const response = await fetch(url_v, {
			method: method,
			headers: {
				"content-type": "application/json",
			},
			body: JSON.stringify(data_v),
		})
		.then(data => {
			console.log(data);
		})
		.catch(error => {
			
		});
	}
	
}

window.onload = function() {
	$("button").on("click", async function(ev) {
		let evo = $(ev.currentTarget);
		
		let url_v = evo.siblings("span").text();
		let param_v = {};
		
		param_o = evo.closest(".card").find(".parameter")
		let length = param_o.find("div").length;
		
		for(let i=0; i<length; i++) {
			let label_v = $(param_o.find("label")[i]).text();
			let value_v = param_o.find("input")[i].value;
			
			param_v[label_v] = value_v;
		}
		
		const data = await promise.fetch(url_v, param_v, "POST");
	});
}

