package com.walker.controller.component;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;

public class UrlTraversal {

	private String className;
	private String methodName;
	private Set<String> accessUrls;
	private List<String> params;
	private Set<RequestMethod> reqMethods;
	private String returnClazzName;
	private Set<NameValueExpression<String>> headers;
	
	private String needLogin;//需要登录
	private String needLimit;//访问频率控制
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Set<String> getAccessUrls() {
		return accessUrls;
	}
	public void setAccessUrls(Set<String> accessUrls) {
		this.accessUrls = accessUrls;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	public Set<RequestMethod> getReqMethods() {
		return reqMethods;
	}
	public void setReqMethods(Set<RequestMethod> reqMethods) {
		this.reqMethods = reqMethods;
	}
	public String getReturnClazzName() {
		return returnClazzName;
	}
	public void setReturnClazzName(String returnClazzName) {
		this.returnClazzName = returnClazzName;
	}
	public Set<NameValueExpression<String>> getHeaders() {
		return headers;
	}
	public void setHeaders(Set<NameValueExpression<String>> headers) {
		this.headers = headers;
	}
	public String getNeedLogin() {
		return needLogin;
	}
	public void setNeedLogin(String needLogin) {
		this.needLogin = needLogin;
	}
	public String getNeedLimit() {
		return needLimit;
	}
	public void setNeedLimit(String needLimit) {
		this.needLimit = needLimit;
	}
	
}
