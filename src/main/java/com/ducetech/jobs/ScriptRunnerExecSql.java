package com.ducetech.jobs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScriptRunnerExecSql {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private DruidDataSource dataSource;
	
	public ScriptRunnerExecSql() {
	}
	
	public ScriptRunnerExecSql(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void execute() throws SQLException, IOException{
            Connection connection = dataSource.getConnection();
            ScriptRunner runner = new ScriptRunner(connection);
            Resources.setCharset(Charset.forName("utf-8")); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            runner.runScript(Resources.getResourceAsReader("sql/pms.sql"));
            runner.closeConnection();
            connection.close();
            log.info("-------------------------------------------");
            log.info("--------------sql脚本执行完成---------------");
            log.info("-------------------------------------------");
	}
}
