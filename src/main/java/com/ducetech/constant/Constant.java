package com.ducetech.constant;

/** 
* @ClassName: Constant  
* @author chensf
* @date 2016年7月29日 上午10:42:10 
* @Description: 常量定义
*/
public class Constant {

	//redis频道  系统频道
	public static final  String SYS_CHANNEL_MAIN="SYS_CHANNEL_MAIN";
	
	//消息类型
	public static final  String NOTICE_TYPE_ALL="NOTICE_TYPE_ALL";			//发送给所有人的消息
	
	public static final  String NOTICE_TYPE_SYS="NOTICE_TYPE_SYS";			//系统消息，发送给指定用户
	
	public static final  String NOTICE_TYPE_PROC="NOTICE_TYPE_PROC";		//流程提醒，发送给指定用户
	
	public static final  String NOTICE_TYPE_INFO="NOTICE_TYPE_INFO";		//集团通知，发送给指定用户

	//集团消息发布人刷新页面
	public static final  String NOTICE_TYPE_INFO_SUCCESS="NOTICE_TYPE_INFO_SUCCESS"; //集团通知，发送成功
	
	public static final  String NOTICE_TYPE_PROC_SUCCESS="NOTICE_TYPE_PROC_SUCCESS"; //流程提醒，发送成功
	
	//公共
	public static final  String NOT_DELETE = "0";    	//未删除
	public static final  String DELETE = "1";			//已删除	
	
	
	
	//集团消息
	public static final  String  MSG_ROLE_UNREAD = "0";  	//消息未读
	
	public static final  String  MSG_ROLE_READ = "1";		//消息已读
	
	public static final  String  MSG_SEND_IN ="1";       	//消息发送中
	
	public static final  String  MSG_SEND_SUCCESS="2";		//消息发送成功
	
	public static final  String  MSG_SEND_LOSE="0";			//消息发送失败	
	
	//流程实例
	public static final  String  PROCINST_TYPE_ORDI="0";    //普通流程
	
	public static final  String  PROCINST_TYPE_TEMP="1";	//临时流程
	
	public static final  String  PROCINST_COMPLETE="1";     //已完成
	
	public static final  String  PROCINST_UNCOMPLETE="0"; 	//未完成
	
   	//工单
	public static final  String  TASK_TYPE_ORDI="0";    //普通流程
	
	public static final  String  TASK_TYPE_TEMP="1";	//临时流程
	
	public static final  String  TASK_COMPLETE="1";     //已完成
	
	public static final  String  TASK_UNCOMPLETE="0"; 	//未完成
	
	public static final  String  TASK_NULLIFY="2";		//作废
	
	public static final  String  TASK_IS_UNBACK="0";	//未退回
	
	public static final  String  TASK_IS_BACK="1";		//已退回
	
	public static final  String  TASK_IS_BACK_TWO="2";  //部室处理完成（临时任务）
	
	public static final  String  TASK_IS_BACK_THREE="3";//相关部室建议完成（临时任务）
	
	//节点
	public static final  String	NODE_START="0";			//起始节点
	
	public static final  String	NODE_END="-1";			//末尾节点
	
	public static final  String NODE_TYPE_ORDINARY="0";		//普通节点
	
	public static final  String NODE_TYPE_APPROVE="1";		//审批节点
	
	//系统通知
	public static final  String NOTICE_NOTICETYPE_DISPOSE="0"; 	//处理类型通知
	
	public static final  String NOTICE_NOTICETYPE_REMIND="1";  	//定时类型通知
	
	public static final  String NOTICE_ROLE_UNREAD = "0";  	//通知未读
	
	public static final  String NOTICE_ROLE_READ = "1";		//通知已读
	
	//节点颜色状态
	public static final  String NODE_GRAY="0";       	//节点灰色
	public static final  String NODE_GREEN="1";			//节点绿色
	public static final  String NODE_RED="2";			//节点红色
	public static final  String NODE_YELLOW="3";		//节点黄色
	
	public static final String SYSTEM_PROP_FILE_NAME = "system.properties";
	
}
