$("#getDetailBtn").click(function() {
	
	let params = {
		idx_member : 2,
	}
	
	fetch("/api/v1/mypage/detail", {
		method: "POST",
		body: JSON.stringify(params),
		headers: {
            "content-type": "application/json",
        },
	})
	.then(result => {
		console.log(result.json());
	})
	.catch(error => {
		
	});
});

$("#modifyBtn").click(function() {
	
	let data = {
		idx_member : 2,
		username: $("#username").val(),
	}
	
	let jsonData = JSON.stringify(data);
	let blobData = new Blob([jsonData], {type: "application/json"});

	let formData = new FormData();

	formData.append("param", blobData);
	formData.append("profile", $("#profile")[0].files[0]);	

	fetch("/api/v1/mypage/modify/info", {
		method: "POST",
		body: formData,      
		headers: {
           //"content-type": "multipart/form-data",
        }, 
	})
	.then(result => {
		console.log(result.json());
	})
	.catch(error => {
		
	});
});

$("#keywordBtn").click(function() {
	
	let keywords = [];
	$("input[name=keywordChk]").each(function () {
	    if($(this).is(":checked") == true) {
			keywords.push($(this).val());
		}
	});
	
	let params = {
		idx_member : 2,
		keyword : keywords
	}
	
	fetch("/api/v1/mypage/modify/keyword", {
		method: "POST",
		body: JSON.stringify(params),
		headers: {
            "content-type": "application/json",
        },
	})
	.then(result => {
		console.log(result.json());
	})
	.catch(error => {
		
	});
});
