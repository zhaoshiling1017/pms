package com.ducetech.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.pms.service.ProcNodeService;

/** 
* @ClassName: ProcNodeController  
* @author gaoy
* @date 2016年9月6日 下午4:04:24 
* @Description: 流程节点Controller
*/
@Controller
@RequestMapping("/procNodes")
public class ProcNodeController {
	
	@Autowired
	private ProcNodeService procNodeService;
	
	/** 
	* @Title: node  
	* @param model
	* @return String
	* @Description: 流程节点
	*/
	@RequestMapping(value = "/node", method = RequestMethod.GET)
	public String node(Model model) {
		
		return "/pms/process/process-node";
	}
	
	
}
