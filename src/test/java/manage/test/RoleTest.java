package manage.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ducetech.framework.model.User;
import com.ducetech.framework.service.UserService;
import com.ducetech.util.CachePool;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RoleTest {

	@Autowired
	private UserService userService;
	
	/**
	 * 
	* @Title: putCacheItem 
	* @Description: 角色缓存
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void putCacheItem(){
		CachePool cachePool = CachePool.getInstance();
		List<User> users = userService.getUsersByRoleId("2");
		cachePool.putCacheItem("2", users);
		List<User> item = cachePool.getCacheItem("2");
		System.out.println(item.get(1).getUserId());
	}
	
//	@Test
//	public void getCacheItem(){
//		CachePool cachePool = CachePool.getInstance();
//	}
	
}
