$(document).ready(function() {
	$("#board").summernote({
		  height: 200,            
		  minHeight: 100,             
		  maxHeight: 300, 
		  focus: true,
		  lang: "ko-KR",
		  placeholder: "내용을 작성해 주세요."
	});
});

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
		content : $('#board').summernote("code"),
		keyword : keywords
	}
	
	fetch("/api/v1/post/write", {
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