$("#btn").click(function() {
	
	let params = {
		test : "asdf",
	}
	
	fetch("/api/v1/auth/login", {
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
