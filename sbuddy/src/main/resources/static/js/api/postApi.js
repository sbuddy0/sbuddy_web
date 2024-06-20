
/**
 * 글 작성
 */
$("#postingBtn").click(function() {
	
	let keywords = [];
	$("input[name=keywordChk]").each(function () {
	    if($(this).is(":checked") == true) {
			keywords.push($(this).val());
		}
	});
	
	let params = {
		idx_member : 2,
		title : $("#title").val(),
		content : $("#board").val(),
		keyword : keywords
	}
	
	
	let jsonData = JSON.stringify(params);
	let blobData = new Blob([jsonData], {type: "application/json"});

	let formData = new FormData();

	formData.append("param", blobData);
	formData.append("file", $("#file")[0].files[0]);	

	fetch("/api/v1/post/write", {
		method: "POST",
		body: formData,
		headers: {
            //"content-type": "application/json",
        },
	})
	.then(result => {
		console.log(result.json());
	})
	.catch(error => {
		
	});

});

/**
 * 내 게시글
 */
$("#getMyPostBtn").click(function() {
	let params = {
		idx_member : 2,
	}

	fetch("/api/v1/post/my/list", {
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

/**
 * 글 삭제
 */
$("#deletetBtn").click(function() {
	let params = {
		idx_member : 2,
		idx_post : $("#deletePost").val()
	}

	fetch("/api/v1/post/delete", {
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

/**
 * 텍스트 검색
 */
$("#searchTextBtn").click(function() {
	let params = {
		idx_member : 2,
		search : $("#search_text").val()
	}

	fetch("/api/v1/post/search/text", {
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

/**
 * 키워드 검색
 */
$("#searchKeywordBtn").click(function() {
	let params = {
		idx_member : 2,
		idx_keyword : $("#search_keyword").val()
	}

	fetch("/api/v1/post/search/keyword", {
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