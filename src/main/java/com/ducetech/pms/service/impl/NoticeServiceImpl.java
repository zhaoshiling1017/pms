package com.ducetech.pms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.framework.dao.RoleDAO;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.pms.dao.NoticeDAO;
import com.ducetech.pms.dao.ProcdefDAO;
import com.ducetech.pms.dao.ProcinstDAO;
import com.ducetech.pms.dao.TaskDAO;
import com.ducetech.pms.model.Notice;
import com.ducetech.pms.model.NoticeRole;
import com.ducetech.pms.model.Procdef;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.service.NoticeService;
import com.ducetech.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/** 
* @ClassName: NoticeServiceImpl  
* @author gaoy
* @date 2016年9月6日 下午4:45:20 
* @Description: 系统通知Service实现类
*/
@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private ProcdefDAO procdefDAO;
	
	@Autowired
	private ProcinstDAO procinstDAO;
	
	@Autowired
	private TaskDAO taskDAO;

	@Override
	public List<Notice> getAllNotice() {
		return this.getNoticeByPager(new Query<Notice>(new Notice())).getResults();
	}

	@Override
	public List<Notice> getNoticeByQuery(Notice notice) {
		List<Notice> notices = noticeDAO.selectNotice(notice);
		return notices;
	}
	
	@Override
	public PagerRS<Notice> getNoticeByUserId(Query<Notice> query, String userId) {
		List<Role> roles = roleDAO.selectRolesByUserId(userId);
		List<String> roleIds = new ArrayList<String>();
		if(roles!=null && roles.size()>0){
			for (Role role : roles) {
				roleIds.add(role.getRoleId());
			} 
		}
		PagerRS<Notice> pagerRS = this.getNoticeByRoleIds(query, roleIds);
		return pagerRS;
	}
	
	@Override
	public PagerRS<Notice> getNoticeByRoleIds(Query<Notice> query, List<String> roleIds) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Notice> notices = new ArrayList<Notice>();
		if(roleIds.size()>0){
			notices = noticeDAO.selectNoticeByRoleIds(query.getT(), roleIds);
		}
		for(Notice notice : notices){
			if(StringUtils.isNotEmpty(notice.getSendTime())){
				notice.setSendTime(DateUtil.formatDate(notice.getSendTime()));
			}		
			if(StringUtils.isNotEmpty(notice.getProcdefId())){
				Procdef procdef = procdefDAO.selectProcdefByProcdefId(notice.getProcdefId());
				if(procdef!=null){
					notice.setProcdef(procdef);
				}
			}
			if(StringUtils.isNotEmpty(notice.getProcinstId())){
				Procinst procinst = procinstDAO.selectProcinstByprocinstId(notice.getProcinstId());
				if(procinst!=null){
					notice.setProcinst(procinst);
				}
			}
			if(StringUtils.isNotEmpty(notice.getTaskId())){
				Task task = taskDAO.selectTaskByTaskId(notice.getTaskId());
				if(task!=null){
					notice.setTask(task);
				}
			}
			if(StringUtils.isNotEmpty(notice.getRoleId())){
				Role role = roleDAO.selectRoleById(notice.getRoleId());
				if(role!=null){
					notice.setRole(role);
				}
			}
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(notices);
		PagerRS<Notice> pagerRS = new PagerRS<Notice>(notices, page.getTotal(), page.getPages());
		return pagerRS;
	}
	
	@Override
	public PagerRS<Notice> getNoticeByPager(Query<Notice> query) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Notice> notices = noticeDAO.selectNotice(query.getT());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(notices);
		PagerRS<Notice> pagerRS = new PagerRS<Notice>(notices, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public Notice gettNoticeById(String noticeId) {
		Notice notice = noticeDAO.selectNoticeById(noticeId);
		return notice;
	}

	@Override
	public void addNotice(Notice notice) {
		noticeDAO.insertNotice(notice);
	}

	@Override
	public void updateNotice(Notice notice) {
		noticeDAO.updateNotice(notice);
	}

	@Override
	public PagerRS<Notice> getNoticeByNoticeRole(Query<NoticeRole> query) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Notice> notices = noticeDAO.selectNoticeByNoticeRole(query.getT());
		for(Notice notice : notices){
			if(StringUtils.isNotEmpty(notice.getSendTime())){
				notice.setSendTime(DateUtil.formatDate(notice.getSendTime()));
			}		
			if(StringUtils.isNotEmpty(notice.getProcdefId())){
				Procdef procdef = procdefDAO.selectProcdefByProcdefId(notice.getProcdefId());
				if(procdef!=null){
					notice.setProcdef(procdef);
				}
			}
			if(StringUtils.isNotEmpty(notice.getProcinstId())){
				Procinst procinst = procinstDAO.selectProcinstByprocinstId(notice.getProcinstId());
				if(procinst!=null){
					notice.setProcinst(procinst);
				}
			}
			if(StringUtils.isNotEmpty(notice.getTaskId())){
				Task task = taskDAO.selectTaskByTaskId(notice.getTaskId());
				if(task!=null){
					notice.setTask(task);
				}
			}
			if(StringUtils.isNotEmpty(notice.getRoleId())){
				Role role = roleDAO.selectRoleById(notice.getRoleId());
				if(role!=null){
					notice.setRole(role);
				}
			}
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(notices);
		PagerRS<Notice> pagerRS = new PagerRS<Notice>(notices, page.getTotal(), page.getPages());
		return pagerRS;
	}
}
