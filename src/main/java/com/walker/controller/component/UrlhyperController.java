package com.walker.controller.component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = UrlhyperController.DIR)
public class UrlhyperController {
	public static final String DIR = "/admin/urlhyper";
	@Autowired
	UrlTraversalComponent urlTraversalComponent;

	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping("show")
	@ResponseBody
	public String show(HttpServletRequest request) {
		String domainName = request.getServerName();
		urlTraversalComponent.setDomainName(domainName);
		List<UrlTraversal> resultList = urlTraversalComponent.generate();

		String content = JSON.toJSONString(resultList);
		System.out.println(content);
		return content;
	}

}
