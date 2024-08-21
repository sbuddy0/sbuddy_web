package com.sbuddy.web.config;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {

	private HashMap<String, Object> param;
	
	public CustomHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.param = new HashMap<String, Object>(request.getParameterMap());
	}
	
	public String getParameter(String name) {
		String returnValue = null;
		String[] paramArray = getParameterValues(name);
		if(paramArray != null && paramArray.length > 0) {
			returnValue = paramArray[0];
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public Map getParameterMap() {
		return Collections.unmodifiableMap(param);
	}
	
	@SuppressWarnings("unchecked")
	public Enumeration getParameterNames() {
		return Collections.enumeration(param.keySet());
	}
	
	public String[] getParameterValues(String name) {
		String[] result = null;
		String[] temp = (String[]) param.get(name);
		if(temp != null) {
			result = new String[temp.length];
			System.arraycopy(temp, 0, result, 0, temp.length);
		}
		return result;
	}
	
	public void setParameter(String name, String value) {
		String[] oneParam = {value};
		setParameter(name, oneParam);
	}
	
	public void setParameter(String name, String[] value) {
		param.put(name, value);
	}
}
