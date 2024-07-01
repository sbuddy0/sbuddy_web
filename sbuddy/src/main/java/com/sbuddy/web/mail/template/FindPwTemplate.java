package com.sbuddy.web.mail.template;

public class FindPwTemplate extends BasicTemplate {

	private String name;
	private String id;
	private String new_password;
	
	public FindPwTemplate() {
		setTitle("[Sbuddy] 임시 비밀번호 안내");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	@Override
	public String getContent() {
		StringBuilder content = new StringBuilder();
		
		content.append("<div>");
			content.append("<h1>임시 비밀번호가 생성되었습니다.</h1>");
			content.append("<br/><br/>");
			content.append("<p>");
				content.append("안녕하세요.<b>" + name + "</b>님.");
				content.append("임시 비밀번호가 생성되었습니다.");
				content.append("로그인 후 비밀번호를 변경해 주시기 바랍니다.");
			content.append("</p>");
			content.append("<p>");
				content.append("아이디: " + id + "<br/>");
				content.append("임시 비밀번호: " + new_password);
			content.append("</p>");	
		content.append("</div>");
		
		return content.toString();
	}
}