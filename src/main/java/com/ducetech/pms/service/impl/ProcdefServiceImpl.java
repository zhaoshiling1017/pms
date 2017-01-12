package com.ducetech.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.dao.ProcdefDAO;
import com.ducetech.pms.model.Procdef;
import com.ducetech.pms.service.ProcdefService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/** 
* @ClassName: ProcdefServiceImpl  
* @author gaoy
* @date 2016年9月6日 下午2:42:41 
* @Description: 流程定义Service实现类
*/
@Service
public class ProcdefServiceImpl implements ProcdefService {
	
	@Autowired
	private ProcdefDAO procdefDAO;

	@Override
	public List<Procdef> getAllProcdef() {
		return this.getProcdefByPager(new Query<Procdef>(new Procdef())).getResults();
	}

	@Override
	public List<Procdef> getProcdefByQuery(Procdef procdef) {
		return procdefDAO.selectProcdef(procdef);
	}

	@Override
	public PagerRS<Procdef> getProcdefByPager(Query<Procdef> query) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Procdef> procdefs = procdefDAO.selectProcdef(query.getT());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(procdefs);
		PagerRS<Procdef> pagerRS = new PagerRS<Procdef>(procdefs, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public Procdef getProcdefByProcdefId(String procdefId) {
		return procdefDAO.selectProcdefByProcdefId(procdefId);
	}

	@Override
	public List<Procdef> getProcdefsByUserId(String userId) {
		return procdefDAO.selectProcdefsByUserId(userId);
	}
}
