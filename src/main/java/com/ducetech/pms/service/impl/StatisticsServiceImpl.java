package com.ducetech.pms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.dao.StatisticsDAO;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Statistics;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.StatisticsService;

/** 
* @ClassName: StatisticsServiceImpl  
* @author gaoy
* @date 2016年9月29日 上午9:54:08 
* @Description: 统计service实现类
*/
@Service
public class StatisticsServiceImpl implements StatisticsService {
	
	@Autowired
	private StatisticsDAO statisticsDAO;
	
	@Autowired
	private ProcinstService procinstService;
	
	@Override
	public List<Statistics> getLaunchStatistics(Statistics statistics) {
		if(StringUtils.isEmpty(statistics.getYear())){
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String formatDate = sdf.format(date);
			statistics.setYear(formatDate);
		}
		List<Statistics> statList = statisticsDAO.selectLaunchStatistics(statistics);
		List<Statistics> statResult = new ArrayList<Statistics>(0);
		for(int i=0;i<12;i++){
			statResult.add(new Statistics());
			statResult.get(i).setDate(i+1+"月");
			statResult.get(i).setCount("0");
			for(int j=0;j<statList.size();j++){
				if((Integer.parseInt(statList.get(j).getDate())-1)==(i)){
					statResult.get(i).setDate(Integer.parseInt(statList.get(j).getDate())+"月");
					statResult.get(i).setCount(statList.get(j).getCount());
				}
			}
		}
		return statResult;
	}

	@Override
	public PagerRS<Procinst> getLaunchTable(Query<Procinst> query) {
		PagerRS<Procinst> result = procinstService.getProcinstByPager(query);
		return result;
	}

}
