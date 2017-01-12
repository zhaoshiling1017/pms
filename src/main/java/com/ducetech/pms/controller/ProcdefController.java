package com.ducetech.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.framework.controller.BaseController;
import com.ducetech.pms.service.ProcdefService;

/** 
* @ClassName: ProcdefController  
* @author gaoy
* @date 2016年9月6日 下午2:39:09 
* @Description: 流程定义Controller
*/

@Controller
@RequestMapping("/procdefs")
public class ProcdefController extends BaseController{
	
	@Autowired
	private ProcdefService procdefService;
	
	/** 
	* @Title: process  
	* @param model
	* @return String
	* @Description: 流程维护主页
	*/
	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String process(Model model) {
		
		return "/pms/process/process";
	}
	
	/** 
	* @Title: processEdit  
	* @param model
	* @return String
	* @Description: 流程编辑页面
	*/
	@RequestMapping(value = "/processEdit", method = RequestMethod.GET)
	public String processEdit(Model model) {
		
		return "/pms/process/process";
	}
	
	/** 
	* @Title: procdefDetail  
	* @param model
	* @return String
	* @Description: 流程定义详情
	*/
	@RequestMapping(value = "/procdef/detail", method = RequestMethod.GET)
	public String procdefDetail(Model model) {
		
		return "/pms/process/procdef-detail";
	}
}
