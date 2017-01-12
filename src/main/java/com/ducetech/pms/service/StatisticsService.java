package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Statistics;

/** 
* @ClassName: StatisticsService  
* @author gaoy
* @date 2016年9月29日 上午9:53:46 
* @Description: 统计service接口
*/
public interface StatisticsService {

	/** 
	* @Title: getLaunchStatistics  
	* @param statistics
	* @return List<Statistics>
	* @Description: 流程启动统计图数据获取
	*/
	List<Statistics> getLaunchStatistics(Statistics statistics);

	/** 
	* @Title: getLaunchTable  
	* @param statistics
	* @return List<Procinst>
	* @Description: 流程启动统计列表数据获取
	*/
	PagerRS<Procinst> getLaunchTable(Query<Procinst> query);

}
