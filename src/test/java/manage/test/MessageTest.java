package manage.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.model.Message;
import com.ducetech.pms.model.MsgRole;
import com.ducetech.pms.service.MessageService;
import com.ducetech.pms.service.MsgRoleService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MessageTest {

	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@Autowired
	MsgRoleService msgRoleService;

	/*@Test
	public void readMessage(){
		MsgRole msgRole = msgRoleService.getMsgRoleByMsgId("2");
		msgRole.setStatus("1");
		msgRole.setReadTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		msgRoleService.updateMsgRole(msgRole);
		
	}*/
	
	
	@Test
	public void delMsgRole(){
		MsgRole msgRole = msgRoleService.getMsgRoleById("1");
		msgRole.setIsDeleted("1");
		msgRole.setDelTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		msgRoleService.deleteMsgRole(msgRole);
	}
	
	
	/**
	 * 
	* @Title: delMessage 
	* @Description: 删除集团消息（假删除） 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void delMessage(){
		Message msg = messageService.getMessageById("1");
		System.out.println(JSON.toJSONString(msg));
		msg.setIsDeleted("1");
		msg.setDelTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		messageService.deleteMessage(msg);
	}
	
	/**
	 * 
	* @Title: updateMsgRole 
	* @Description: 更新以读未读状态 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	//@Test
	public void updateMsgRole(){
		MsgRole msgRole =  msgRoleService.getMsgRoleById("1");
		msgRole.setStatus("1");
		msgRole.setReadTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		msgRoleService.updateMsgRole(msgRole);
	}
	
	
	/**
	 * 
	* @Title: getMessage 
	* @Description: 获得消息
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	//@Test
	public void getMessage(){
		Message message  = messageService.getMessageById("1");		
		System.out.println(JSON.toJSONString(message));
	}
	
	/**
	 * 
	* @Title: getMsgRole 
	* @Description: 获得所有人员消息 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	//@Test
//	public void getMsgRole(){
//		MsgRole msgRole = new MsgRole();
//		List<MsgRole> msgRoles = msgRoleService.getMsgRoles(msgRole);
//		System.out.println(JSON.toJSONString(msgRoles));
//	}
	
	
	/**
	 * 
	* @Title: saveMessage 
	* @Description: 增加一条消息 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	//@Test
	public void saveMessage(){
		Message message = new Message();
		message.setMsgTitle("明天放假 ！！！");
		message.setContent("因为明天天气好，所以决定放假 一天。");
		message.setPublishTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		message.setPublisherId("1");
		message.setIsDeleted("0");
		messageService.addMessage(message);
		System.out.println(message.getMsgId());
	}
	
	/**
	 * 
	* @Title: msgRole 
	* @Description: 给人员插入消息
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	//@Test
	public void msgRole(){
		Message message = new Message();
		message.setMsgTitle("明天放假 ！！！");
		message.setContent("因为明天天气好，所以决定放假 一天。");
		message.setPublishTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		message.setPublisherId("1");
		message.setIsDeleted("0");
		messageService.addMessage(message);
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		Set<String> users = new HashSet<String>();
//		List<User> users = new ArrayList<User>();
		for(int i=0;i<list.size();i++){
			if(userService.getUsersByRoleId(list.get(i)).size()>0){
				for (int j=0;j<userService.getUsersByRoleId(list.get(i)).size();j++) {
					System.out.println(userService.getUsersByRoleId(list.get(i)).get(j).getUserId());
					users.add(userService.getUsersByRoleId(list.get(i)).get(j).getUserId());
				} 
			}
		}
		for (String userId:users) {
			MsgRole msgRole = new MsgRole();
			msgRole.setMsgId(message.getMsgId());
			msgRole.setUserId(userId);
			msgRole.setStatus("0");
			msgRole.setUserName(userService.getUserByUserId(userId).getName());
			msgRoleService.addMsgRole(msgRole);
		}
//		msgRoleService.getMsgRole(list, message);
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		message.setStatus("1");
//		messageService.updateMessage(message);
		System.out.println("结束");
	}
}
