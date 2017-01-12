package com.ducetech.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.ducetech.framework.dao.RoleDAO;
import com.ducetech.framework.dao.UserDAO;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.util.CachePool;
import com.ducetech.jobs.ScriptRunnerExecSql;
import com.ducetech.util.DuceTechUtils;
import com.ducetech.util.MyCache;


@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private BasicDataSource dataSource;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			initDatabase();
			initRoleIdsCache();
        }
	}
	
	private void initDatabase(){
		String isInitDatabase;
		try {
			isInitDatabase = DuceTechUtils.readProp("isInitDatabase");
			if(Boolean.parseBoolean(isInitDatabase)){
				ScriptRunnerExecSql execSql = new ScriptRunnerExecSql(dataSource);
				execSql.execute();
			}
		} catch (IOException e) {
			log.info("配置文件读取出错!");
			e.printStackTrace();
		} catch (SQLException e) {
			log.info("sql文件执行错误!");
			e.printStackTrace();
		}
	}
	
	public void initRoleIdsCache(){
		CachePool cachePool = CachePool.getInstance();
		MyCache myCache = new MyCache();
		List<Role> roles = roleDAO.selectRole(new Role());
		for (Role role : roles) {
			role.getRoleId();
			List<User> users = userDAO.selectUsersByRoleId(role.getRoleId());
			List<String> ids = new ArrayList<>();
			for (int i = 0; i < users.size(); i++) {
				ids.add(users.get(i).getUserId());
			}
			cachePool.putCacheItem(role.getRoleId(), users);
			myCache.getRoleIdsCache().put(role.getRoleId(),ids);
		}
		log.info("-------------------------------------------");
		log.info("--------------缓存初始化完成----------------");
		log.info("-------------------------------------------");
	}
}
