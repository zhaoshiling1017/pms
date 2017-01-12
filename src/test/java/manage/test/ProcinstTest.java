package manage.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.RoleService;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.model.ProcNode;
import com.ducetech.pms.model.Procdef;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.service.ProcNodeService;
import com.ducetech.pms.service.ProcdefService;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.TaskService;
import com.ducetech.util.DateUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProcinstTest {
	
	@Autowired
	private ProcdefService procdefService;
	
	@Autowired
	private ProcinstService procinstService;
	
	@Autowired
	private ProcNodeService procNodeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Test			//获取可发起的流程
	public void getStartProcess(){
		String userId = "1";
//		User user = userService.getUserByUserId(userId);
		List<Procdef> procdefs = procdefService.getProcdefsByUserId(userId);
		System.err.println(procdefs.size());
		System.err.println(JSON.toJSONString(procdefs));
	}
	
	@Test			//发起普通流程
	public void startProcess(){
		String procdefId = "1";
		String userId = "1";
		User user = userService.getUserByUserId(userId);
		Procdef pf = procdefService.getProcdefByProcdefId(procdefId);
		//发起普通流程生成流程实例
		Procinst procinst = new Procinst();
		procinst.setProcdefId(pf.getProcdefId());
		procinst.setProcType(pf.getProcType());
		procinst.setProcName(pf.getProcName());
		procinst.setProcinstCode("PROC20160914001");
		procinst.setComment("流程备注");
		procinst.setStatus("0");	//0未完成，1已完成，2已作废
		procinst.setStartPersonId(userId);
		procinst.setStartTime(DateUtil.dateTimeToString(new Date()));
		procinstService.addProcinst(procinst);	//发起普通流程
		//通过流程定义获取起始节点
		ProcNode node = new ProcNode();
		node.setProcdefId(procinst.getProcdefId());
		node.setUpNodeId("0"); 
		node.setIsDeleted("0");
		List<ProcNode> procNodes = procNodeService.getProcNodeByQuery(node);	//按流程定义ID获取起始节点
		Task task = new Task();
		task.setProcinstId(procinst.getProcinstId());
		task.setProcinstCode(procinst.getProcinstCode());
		task.setProcType(procinst.getProcType());
		task.setComment("备注信息");
		task.setStartTime(DateUtil.dateTimeToString(new Date()));
		task.setStartPersonId(user.getUserId());
		task.setStartDeptId(user.getDepartmentId());
		task.setUpNodeId("0");
		//task.setTimeLimit();
		taskService.createTask(task, procNodes);
		System.err.println("=====================");
		System.err.println("startProcess:success");
	}
	
	@Test		//登录人获取工单
	public void getTaskByUserId(){
		String userId = "2";
		Task task = new Task();						//设置工单查询条件
		task.setStatus("0");
		Query<Task> query = new Query<Task>();		//将task放入query并设置分页参数
		query.setT(task);
		PagerRS<Task> pageRS = taskService.getTasksByUserId(query, userId);	//按人员ID获取工单 
		System.err.println("=====================");
		System.err.println(JSON.toJSONString(pageRS.getCount()));
		System.err.println(JSON.toJSONString(pageRS.getResults()));
	}
	
	@Test		//与我相关
	public void getMyRelated(){
		String userId = "12";	//安质部
		Procinst procinst = new Procinst();
		procinst.setStatus("0");
		procinst.setProcdefId("");
		procinst.setProcinstCode("");
		Query<Procinst> Query = new Query<Procinst>();
		Query.setT(procinst);
		PagerRS<Procinst> pageRS = procinstService.getMyRelatedByUserId(Query, userId);	//按userId获取与自己相关的流程实例
		System.err.println("=====================");
		System.err.println(JSON.toJSONString(pageRS.getCount()));
		System.err.println(JSON.toJSONString(pageRS.getResults()));		//正在启动与自己相关的流程
		/*System.err.println(JSON.toJSONString(pageRS.getResults().get(0).getTasks().size()));	//一个流程正在启动的工单数量
		System.err.println(JSON.toJSONString(pageRS.getResults().get(0).getTasks()));		//一个流程正在启动的工单	*/	
	}
	
	@Test		//获取普通任务处理
	public void getTaskDispose(){
		String taskId = "7";
		//获取流程及工单信息
		Task task = taskService.getTaskByTaskId(taskId);	//工单信息
//		Procinst procinst = procinstService.getProcinstByProcinstId(task.getProcinstId());	//流程实例信息
		Task t = new Task();
		t.setStatus("1");
		t.setProcinstId(task.getProcinstId());
//		List<Task> tasks = taskService.getTaskByQuery(t);	//获取流程实例下的工单已完成的节点信息
//		ProcNode upNode = procNodeService.getProcNodeByNodeId(task.getUpNodeId());	//获取上一节点信息
		List<ProcNode> nextNodes = new ArrayList<ProcNode>();	//多个下个节点
		if(StringUtils.isNotEmpty(task.getNextNodeId())){
			String [] nodeIds = task.getNextNodeId().split(",");
			for(String nodeId : nodeIds){
				ProcNode nextNode = procNodeService.getProcNodeByNodeId(nodeId);	//获取下一节点
				nextNodes.add(nextNode);
			}
		}
		System.err.println(JSON.toJSONString(nextNodes));
		/*for(ProcNode node : nextNodes){
			List<Role> roles = roleService.getRolesByNodeId(node.getNodeId());	//获取对下一节点的处理信息,可选角色
		}*/
	}
	
	@Test		//处理工单
	public void taskDispose(){
		
	}
}