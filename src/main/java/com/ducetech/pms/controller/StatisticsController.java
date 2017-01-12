package com.ducetech.pms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.Department;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.service.DepartmentService;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Statistics;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.StatisticsService;
import com.ducetech.util.JsonUtils;

/** 
* @ClassName: StatisticsController  
* @author gaoy
* date 2016年9月19日 上午9:26:00 
* @Description: 统计Controller
*/
@Controller
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {
	
	@Autowired
	private ProcinstService procinstService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private DepartmentService departmentService;
	
	/** 
	* @Title: progress  
	* @param model
	* @return String
	* @Description: 进度管理页面
	*/
	@RequestMapping(value = "/progress", method = RequestMethod.GET)
	public String progress() {
		return "/pms/statistics/progress-manage";
	}
	
	/**
	 * 
	* @Title: progressData 
	* @Description: 进度管理页面数据 
	* @param @param model
	* @param @param request
	* @param @param procinst
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/progressData", method = RequestMethod.POST)
	public void progressData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Query<Procinst> query = JsonUtils.getSearchCondition(Procinst.class, request);
//		if(query.getT()==null){
//			if(StringUtils.isEmpty(query.getT().getStatus())){
//				query.getT().setStatus(Constant.PROCINST_UNCOMPLETE);
//			}
//		}
		//去空字符串
		query.getT().setProcinstCode(query.getT().getProcinstCode().trim());
		query.getT().setProcName(query.getT().getProcName().trim());
		PagerRS<Procinst> pageRS = procinstService.getProcinstByPager(query);	//按userId获取与自己相关的流程实例
		response.getWriter().write(JSON.toJSONString(pageRS,SerializerFeature.DisableCircularReferenceDetect));
	}
	
	
	/** 
	* @Title: launchStati  
	* @param model
	* @return String
	* @Description: 流程启动统计页面
	*/
	@RequestMapping(value = "/launchStati", method = RequestMethod.GET)
	public String launchStati(Model model) {
		return "/pms/statistics/statistic-process";
	}
	
	/** 
	* @Title: launchStatiData  
	* @param model
	* @return String
	 * @throws IOException 
	* @Description: 流程启动统计图数据获取
	*/
	@RequestMapping(value = "/launchStatiData", method = RequestMethod.POST)
	public void launchStatiData(Procinst procinst, HttpServletResponse response) throws IOException {
		Statistics statistics = new Statistics();
		statistics.setProcName(procinst.getProcName());
		statistics.setYear(procinst.getStartTime());
		statistics.setProcType(procinst.getProcType());
		statistics.setProcinstStatus(procinst.getStatus());
		List<Statistics> statList = statisticsService.getLaunchStatistics(statistics);
		response.getWriter().write(JSON.toJSONString(statList));
	}
	
	
	/** 
	* @Title: launchTableData  
	* @param statistics
	* @param response
	 * @throws Exception 
	* @Description: 流程启动统计列表数据获取
	*/
	@RequestMapping(value = "/launchTableData", method = RequestMethod.POST)
	public void launchTableData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Query<Procinst> query = JsonUtils.getSearchCondition(Procinst.class, request);
		if(StringUtils.isEmpty(query.getT().getStartTime())){
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String formatDate = sdf.format(date);
			query.getT().setStartTime(formatDate);
		}else{
			query.getT().setStartTime(query.getT().getStartTime());
		}
		//去空字符串
		query.getT().setProcName(query.getT().getProcName().trim());
		PagerRS<Procinst> result = statisticsService.getLaunchTable(query);
		response.getWriter().write(JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));
	}
	
	/** 
	* @Title: deptStati  
	* @param model
	* @return String
	* @Description: 部门执行统计
	*/
	@RequestMapping(value = "/deptStati", method = RequestMethod.GET)
	public String deptStati(Model model,Procinst procinst) {
		List<Department> depts = departmentService.getAllDepartments();
		model.addAttribute("depts", depts);
		return "/pms/statistics/statistic-department";
	}
	
	/** 
	* @Title: deptStati  
	* @param model
	* @return String
	 * @throws Exception 
	* @Description: 部门执行统计数据
	*/
	@RequestMapping(value = "/deptStatiData", method = RequestMethod.POST)
	public void deptStatiData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Query<Procinst> query = JsonUtils.getSearchCondition(Procinst.class, request);
		PagerRS<Procinst> result = procinstService.getProcinstByDept(query);	//按userId获取与自己相关的流程实例
		response.getWriter().write(JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect));
	}
	
	/**
	 * 
	* @Title: deptStatiByDept 
	* @Description: 普通流程数
	* @param @param procinst
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deptStatiByDept", method = RequestMethod.POST)
	public void deptStatiByDept(Procinst procinst,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> maps = null;
		if(StringUtils.isNotEmpty(procinst.getDeptId())){
			//普通任务条数
			maps = procinstService.getProcinctCountByName(procinst);
		}
		response.getWriter().write(JSON.toJSONString(maps));
	}
	
	/**
	 * 
	* @Title: deptStatiTemp 
	* @Description: 临时流程数
	* @param @param procinst
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deptStatiTemp", method = RequestMethod.POST)
	public void deptStatiTemp(Procinst procinst,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Integer> tempMap=null;
		String result="{";
		if(StringUtils.isNotEmpty(procinst.getDeptId())){
			//临时任务条数
			tempMap = procinstService.getProcinctCountByNameTemp(procinst);
			result +="\"tempMap\":\"" + tempMap.get("num") + "\",";
		}
		Department dept = new Department();
		if(StringUtils.isNotEmpty(procinst.getDeptId())){
			dept = departmentService.getDepartmentById(procinst.getDeptId());
			result +="\"dept\":\"" + dept.getDepartmentName() + "\"";
		}
		result +="}";
		response.getWriter().write(result);
	}
}
