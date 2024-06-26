// json 데이터 전송
const promise_json = {
	fetch: async function(url_v, data_v) {
		return await fetch(url_v, {
			method: "POST",
			headers: {
				"content-type": "application/json",
			},
			body: JSON.stringify(data_v),
		});
	}
}

// 파일첨부 json 데이터 전송
const promise_form = {
	fetch: async function(url_v, formData) {
		return await fetch(url_v, {
			method: "POST",
			headers: {
	        },
			body: formData,
		});
	}
}

window.onload = function() {
	$("button").on("click", async function(ev) {
		let evo = $(ev.currentTarget);
		
		// api url
		let url_v = evo.siblings("span").text();
		let param_v = {};
		
		// 파라미터
		param_o = evo.closest(".card").find(".parameter")
		
		let length = param_o.find("div input").length;
		let label_o =  $(param_o.find("label"));
		let file_v = null;
	
		for(let i = 0; i < length; i++) {
			let label_v = $(label_o[i]).text();
			
			if(label_v != "file") {
				// 파일 제외 파라미터 
				let value_v = param_o.find("input")[i].value;
				param_v[label_v] = value_v;
			} else {
				// 파일
				file_v = $(".file")[0].files[0];
			}
		}
		param_v["idx_member"] = $("#member").val();
		
		console.log(param_v);
		if(file_v != null) {
			console.log(file_v);
		}
		
		let response = "";
		if (file_v == null) {
			response = await promise_json.fetch(url_v, param_v);
		} else {
			// 파일이 있을 경우 데이터를 formData로 변경
			let jsonData = JSON.stringify(param_v);
			let blobData = new Blob([jsonData], {type: "application/json"});
		
			let formData = new FormData();
		
			formData.append("param", blobData);
			formData.append("file", file_v);	
			
			response = await promise_form.fetch(url_v, formData);
		}
		const result = await response.json();
		
		$(param_o.next()).find(".result pre").html(JSON.stringify(result, null, 2));
	});
}

