package com.ducetech.framework.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController extends BaseController {

	
	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model) {
		getLoginUser(request, model);
		return "index";
	}

	@RequestMapping("/notify")
	public String notify(HttpServletRequest request, Model model) {
		getLoginUser(request, model);
		return "index";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String notFound() {
		return "404";
	}
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String error() {
		return "500";
	}
}
