$("#btn").click(function() {
	
	let params = {
		test : "asdf",
	}
	
	fetch("/api/v1/sy/test", {
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
