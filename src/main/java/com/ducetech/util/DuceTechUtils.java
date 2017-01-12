package com.ducetech.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/** 
 * @ClassName: DuceTechUtils  
 * @author chensf
 * @date 2015年12月3日 下午2:41:09 
 * @Description: 工具类
 */
public class DuceTechUtils {
	/** 
	* @Title: readProp  
	* @param key
	* @return String
	* @Description: 读取配置文件中的值
	*/
	public static String readProp(String key) throws IOException{
		Properties prop = new Properties();
		InputStream in = DuceTechUtils.class.getClassLoader().getResourceAsStream("system.properties");
		prop.load(in);
		return prop.getProperty(key);
	}
	
	/** 
	* @Title: isAvailableIP  
	* @param request
	* @return boolean
	* @Description: 判断是否是可用IP
	*/
	public static boolean isAvailableIP(HttpServletRequest request) throws IOException{
		String IPs[] = readProp("availableIP").split(",");
		List<String> IPList = Arrays.asList(IPs);
		if(IPList.contains(request.getRemoteAddr()))
			return true;
		else
			return false;
	}
	
	/** 
	* @Title: name  
	* @param list
	* @return boolean
	* @Description: 判断List中是否有相同的元素
	*/
	public static boolean IsAllelementIdentical(List<? extends Object> list) {
		if(null == list)
            return false;
        return 1 == new HashSet<Object>(list).size();
	}
	
	/** 
	* @Title: removeDuplicate  
	* @param list
	* @return List<String>
	* @Description: 去除List中重复的String类型的元素
	*/
	public static List<String> removeDuplicate(List<String> list) {
		HashSet<String> h = new HashSet<String>(list);
		list.clear();
		list.addAll(h);
		return list;
	}
	
	public static boolean isContainsStr(List<String> list,String str) {
		for (String string : list) {
			string.equals(str);
			return true;
		}
		return false;
	}
}
