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
	
	let params = {
		idx_member : 2,
		username: $("#username").val()
	}
	
	fetch("/api/v1/mypage/modify/info", {
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
