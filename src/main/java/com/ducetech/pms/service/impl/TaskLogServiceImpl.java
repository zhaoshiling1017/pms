package com.ducetech.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.pms.dao.TaskLogDAO;
import com.ducetech.pms.model.TaskLog;
import com.ducetech.pms.service.TaskLogService;
import com.ducetech.util.DateUtil;

@Service
public class TaskLogServiceImpl implements TaskLogService{

	@Autowired
	private TaskLogDAO taskLogDAO;
	
	@Override
	public void addTaskLog(TaskLog taskLog) {
		taskLogDAO.insertTaskLog(taskLog);
		
	}

	@Override
	public List<TaskLog> getTaskLog(TaskLog taskLog) {
		List<TaskLog> taskLogs = taskLogDAO.selectTaskLog(taskLog);
		for (TaskLog taskLog2 : taskLogs) {
			taskLog2.setNodeTime(DateUtil.formatDate(taskLog2.getNodeTime()));
		}
		return taskLogs;
	}

}
