package com.walker.controller.component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class UrlTraversalComponent {
	private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
	/**
	 * 忽略检查的url
	 */
	private Set<String> ignoreUrls;
	private String domainName;
	private boolean status = true;

	public Set<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(Set<String> ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;

	public List<UrlTraversal> generate() {
		List<UrlTraversal> urlTraversalList = new ArrayList<UrlTraversal>();
		if (!getStatus()) {
			return urlTraversalList;
		}
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
				.getHandlerMethods();
		for (Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
			RequestMappingInfo requestMappingInfo = entry.getKey();
			HandlerMethod handlerMethod = entry.getValue();
			PatternsRequestCondition patternsRequestCondition = requestMappingInfo
					.getPatternsCondition();
			Set<String> urls = patternsRequestCondition.getPatterns();
			boolean isIgnore = false;
			for (String url : urls) {
				if (ignoreUrls.contains(url)) {
					isIgnore = true;
					break;
				}
			}
			if (isIgnore) {
				continue;
			}
			RequestMethodsRequestCondition requestMethodsRequestCondition = requestMappingInfo
					.getMethodsCondition();
			Set<RequestMethod> reqMethods = requestMethodsRequestCondition
					.getMethods();
			Set<NameValueExpression<String>> headerExpressions = requestMappingInfo
					.getHeadersCondition().getExpressions();
			String className = handlerMethod.getBeanType().getName();
			Method method = handlerMethod.getMethod();
			String methodName = method.getName();
			Class<?>[] paramClasses = handlerMethod.getMethod()
					.getParameterTypes();
			List<String> params = new ArrayList<String>();
			String paramNames[] = parameterNameDiscoverer
					.getParameterNames(method);
			int index = 0;
			for (Class c : paramClasses) {
				boolean ismatch = isIgnoreType(c);
				if (!ismatch) {
					List<String> propertys = getModelProperty(c);
					params.addAll(propertys);
				} else {
					String type = c.getSimpleName();
					params.add(type + ":" + paramNames[index]);
				}
				index++;
			}
			String returnClazzName = handlerMethod.getMethod().getReturnType()
					.getName();

			UrlTraversal urlTraversal = new UrlTraversal();
			urlTraversal.setClassName(className);
			urlTraversal.setMethodName(methodName);
			urlTraversal.setAccessUrls(urls);
			urlTraversal.setParams(params);
			urlTraversal.setHeaders(headerExpressions);
			urlTraversal.setReqMethods(reqMethods);
			urlTraversal.setReturnClazzName(returnClazzName);
			urlTraversalList.add(urlTraversal);
		}
		return urlTraversalList;
	}

	public List<String> getModelProperty(Class clazz) {
		List<String> propertys = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			propertys.add(f.getType().getSimpleName() + ":" + f.getName());
		}
		return propertys;
	}

	public boolean isIgnoreType(Class clazz) {
		if (clazz.isPrimitive()) {
			return true;
		}
		if (Collection.class.isAssignableFrom(clazz)) {
			return true;
		}
		if (Map.class.isAssignableFrom(clazz)) {
			return true;
		}
		if (HttpServletRequest.class.isAssignableFrom(clazz)) {
			return true;
		}
		if (HttpServletResponse.class.isAssignableFrom(clazz)) {
			return true;
		}
		if (Number.class.isAssignableFrom(clazz)) {
			return true;
		}
		boolean ismatch = false;
		Object objArr[] = { String.class, Model.class, Date.class };
		for (Object obj : objArr) {
			if (clazz.equals(obj)) {
				ismatch = true;
				break;
			}
		}
		return ismatch;
	}

}
