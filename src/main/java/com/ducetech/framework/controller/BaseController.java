package com.ducetech.framework.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ducetech.framework.model.User;
import com.ducetech.framework.service.UserService;
import com.ducetech.util.CookieUtil;

@Controller
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    UserService userService;
    
    /** 基于@ExceptionHandler异常处理 */  
    @ExceptionHandler
    public String exp(HttpServletRequest request, Exception ex) {
        logger.error("ducetech.com exception: {}.", ex.getMessage());
    	request.setAttribute("message", ex.getMessage());
    	
    	/*String  expMessage = ExceptionUtils.getFullStackTrace(ex);
    	List<String> expMessageList = Arrays.asList(expMessage.split("\r\n"));
    	System.out.println(expMessage);
    	request.setAttribute("stackTrace", expMessageList);*/
    	
        //转向错误页面  
        return "500";
    }

    public User getLoginUser(HttpServletRequest request, Model model) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(CookieUtil.USER_NAME);
        if (null == user || StringUtils.isEmpty(user.getName())) {
            String userId = CookieUtil.getLoginUserId(request);
            if (StringUtils.isNotEmpty(userId)) {
                user = userService.getUserByUserId(userId);
            }
        }
        model.addAttribute("user", user);
        return user;
    }

    public User getLoginUser(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(CookieUtil.USER_NAME);
        if (user == null || StringUtils.isEmpty(user.getName())) {
            String userId = CookieUtil.getLoginUserId(request);
            if (StringUtils.isNotEmpty(userId)) {
              user = userService.getUserByUserId(userId);
            }
        }
        return user;
    }
    
}
