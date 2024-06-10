$("#getDetailBtn").click(function() {
	
	let params = {
		idx_member : 1,
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
