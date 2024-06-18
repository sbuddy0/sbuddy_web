$(document).ready(function() {
	$("#board").summernote({
		  height: 200,            
		  minHeight: 100,             
		  maxHeight: 300, 
		  focus: true,
		  lang: "ko-KR",
		  placeholder: "내용을 작성해 주세요.",
		  callbacks: {	
		  	  onImageUpload : function(files) {
				  uploadSummernoteImageFile(files[0],this);
			  },
			  /*
			  onPaste: function(e) {
				  let clipboardData = e.originalEvent.clipboardData;
				  if(clipboardData && clipboardData.items && clipboardData.items.length) {
				      let item = clipboardData.items[0];
					  if(item.kind === 'file' && item.type.indexOf('image/') !== -1) {
						  e.preventDefault();
					  }
				  }
			  }
			  */
		  }
	});
	
	/*
	function uploadSummernoteImageFile(file, editor) {
		data = new FormData();
		data.append("file", file);
		$.ajax({
			data : data,
			type : "POST",
			url : "/uploadSummernoteImageFile",
			contentType : false,
			processData : false,
			success : function(data) {
            	//항상 업로드된 파일의 url이 있어야 한다.
				$(editor).summernote('insertImage', data.url);
			}
		});
	}
	*/
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