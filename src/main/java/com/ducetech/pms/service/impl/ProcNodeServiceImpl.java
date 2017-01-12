package com.ducetech.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.dao.ProcNodeDAO;
import com.ducetech.pms.model.ProcNode;
import com.ducetech.pms.service.ProcNodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/** 
* @ClassName: ProcNodeServiceImpl  
* @author gaoy
* @date 2016年9月6日 下午4:07:08 
* @Description: 流程节点Service实现类
*/
@Service
public class ProcNodeServiceImpl implements ProcNodeService {
	
	@Autowired
	private ProcNodeDAO procNodeDAO;

	@Override
	public List<ProcNode> getAllProcNode() {
		return this.getProcNodeByPager(new Query<ProcNode>(new ProcNode())).getResults();
	}

	@Override
	public List<ProcNode> getProcNodeByQuery(ProcNode procNode) {
		return procNodeDAO.selectProcNode(procNode);
	}

	@Override
	public PagerRS<ProcNode> getProcNodeByPager(Query<ProcNode> query) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<ProcNode> procNodes = procNodeDAO.selectProcNode(query.getT());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(procNodes);
		PagerRS<ProcNode> pagerRS = new PagerRS<ProcNode>(procNodes, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public ProcNode getProcNodeByNodeId(String nodeId) {
		return procNodeDAO.selectProcNodeByNodeId(nodeId);
	}
}
