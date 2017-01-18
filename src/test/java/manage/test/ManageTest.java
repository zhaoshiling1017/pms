package manage.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ducetech.constant.Constant;
import com.ducetech.framework.dao.UserDAO;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.UserService;
import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.Encrypt;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManageTest {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	
	Query<User> query = new Query<>();
	
	@Test
	public void testUserService(){
		
		//query.setPageSize(10);
		PagerRS<User> rs = userService.getUserByPager(query);
		List<User> userList = userService.getUsersByDeptId("57");
		//userService.deleteUserById("1");
		//User user = userService.getUserByUserId("1");
		//System.err.println(user.getLoginName());
		System.err.println(rs.getCount());
		System.err.println(userList.size());
		System.err.println();
	}
	
	@Test
	public void initAllPassword(){
		
		List<User> userList = userService.getAllUsers();
		for (User user : userList) {
			userService.passwordReset(user.getUserId(), Encrypt.md5("admin"));
		}
	}
	
	@Test
	public void addUser(){
		User user = new User();
		/*user.setLoginName("chensf");
		user.setName("陈硕峰");
		user.setPassword("admin");
		user.setGender("男");*/
		
		user.setLoginName("wxdds");
		user.setName("维修调度室");
		user.setPassword("admin");
		user.setGender("男");
		userService.addUser(user);
		System.err.println(user.getUserId());
	}
	
	@Test
	public void redis() {
		//myRedisTemplate.sendMessage("1", Constant.NOTICE_TYPE_INFO);
		myRedisTemplate.setItem("csf", "csf89757");
		System.err.println(myRedisTemplate.getItem("csf"));
	}
}
