package com.ducetech.pms.dao;

import java.util.List;

import com.ducetech.pms.model.Statistics;

/** 
* @ClassName: StatisticsDAO  
* @author gaoy
* @date 2016年9月29日 上午10:06:58 
* @Description: 统计dao接口
*/
public interface StatisticsDAO {

	/** 
	* @Title: selectLaunchStatistics  
	* @param statistics
	* @return List<Statistics>
	* @Description: 流程启动统计
	*/
	List<Statistics> selectLaunchStatistics(Statistics statistics);

}
