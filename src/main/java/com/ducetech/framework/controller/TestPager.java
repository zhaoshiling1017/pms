package com.ducetech.framework.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.constant.Constant;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.UserService;
import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.PagerQueryUtil;

@Controller
@RequestMapping("/test")
public class TestPager extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	
	@RequestMapping(value = "/pager", method = RequestMethod.GET)
	public String testPager(Model model) {
		
		return "/test/pager";
	}
	
	@RequestMapping(value = "/pager/data", method = RequestMethod.POST)
	public void testPagerData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Query<User> query = PagerQueryUtil.getPagerQuery(User.class, request);
		PagerRS<User> rs= userService.getUserByPager(query);
		//System.err.println(rs.toJsonStr());
		response.getWriter().write(rs.toJsonStr());
	}
	
	@RequestMapping(value = "/pager/Websocket", method = RequestMethod.GET)
	public void testWebSocket(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		myRedisTemplate.sendMessage(getLoginUser(request).getUserId(), Constant.NOTICE_TYPE_ALL);
		response.getWriter().write("{\"data\":\"OK\"}");
	}
}
