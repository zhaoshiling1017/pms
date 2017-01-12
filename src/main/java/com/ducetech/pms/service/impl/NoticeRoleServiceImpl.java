package com.ducetech.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.dao.NoticeDAO;
import com.ducetech.pms.dao.NoticeRoleDAO;
import com.ducetech.pms.model.NoticeRole;
import com.ducetech.pms.service.NoticeRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/** 
* @ClassName: NoticeRoleServiceImpl  
* @author gaoy
* @date 2016年10月27日 下午3:19:41 
* @Description: 系统通知角色Service实现类
*/
@Service
public class NoticeRoleServiceImpl implements NoticeRoleService {
	
	@Autowired
	private NoticeRoleDAO noticeRoleDAO;
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public List<NoticeRole> getAllNoticeRole() {
		return this.getNoticeRoleByPager(new Query<NoticeRole>(new NoticeRole())).getResults();
	}

	@Override
	public List<NoticeRole> getNoticeRoleByQuery(NoticeRole noticeRole) {
		List<NoticeRole> noticeRoles = noticeRoleDAO.selectNoticeRole(noticeRole);
		return noticeRoles;
	}

	@Override
	public PagerRS<NoticeRole> getNoticeRoleByPager(Query<NoticeRole> query) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<NoticeRole> noticeRoles = noticeRoleDAO.selectNoticeRole(query.getT());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(noticeRoles);
		PagerRS<NoticeRole> pagerRS = new PagerRS<NoticeRole>(noticeRoles, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public NoticeRole getNoticeRoleById(String Id) {
		NoticeRole noticeRole = noticeRoleDAO.selectNoticeRoleById(Id);
		return noticeRole;
	}

	@Override
	public void addNoticeRole(NoticeRole noticeRole) {
		noticeRoleDAO.insertNoticeRole(noticeRole);
	}

	@Override
	public void updateNoticeRole(NoticeRole noticeRole) {
		noticeRoleDAO.updateNoticeRole(noticeRole);
	}
}
