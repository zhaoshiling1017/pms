package com.ducetech.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ducetech.framework.model.Query;

/** 
 * @ClassName: JsonUtils  
 * @author chensf
 * @date 2015年10月15日 下午4:42:37 
 * @Description: 生成json,主要用于给bootstrap-table插件返回结果
 */
public class JsonUtils {
	
	/** 
	* @Title: printJSONObject  
	* @param object
	* @return String
	* @Description: 将某对象转换为json格式的字符串,如:{"userName":"Tom"}
	*/
	public static String toJSONObjectStr(Object object){
		JSONObject jo = JSONObject.fromObject(object);
		return jo.toString();
	}
	
	/** 
	* @Title: printJSONObject  
	* @param object
	* @return String
	* @Description: 将某对象转换为json格式的字符串,如:{"userName":"Tom"},含有参数JsonConfig用于设置转换的附加条件
	*/
	public static String toJSONObjectStr(Object object,JsonConfig jc){
		JSONObject jo = JSONObject.fromObject(object ,jc);
		return jo.toString();
	}
	
	/** 
	* @Title: printJSONArray  
	* @param object
	* @return String
	* @Description: 将对象的List转换为json格式的字符串,如:[{"name":"张三"},{"name":"李四"}]
	*/
	public static String printJSONArray(Object list){
		JSONArray ja = JSONArray.fromObject(list);
		return ja.toString();
	}
	
	/** 
	* @Title: printJSONArray  
	* @param list
	* @param jc
	* @return String
	* @Description: 将对象的List转换为json格式的字符串,如:[{"name":"张三"},{"name":"李四"}],对输出的对象就行自定义设置
	*/
	public static String printJSONArray(Object list, JsonConfig jc){
		JSONArray ja = JSONArray.fromObject(list, jc);
		return ja.toString();
	}
	
	/** 
	* @Title: printJSONArray  
	* @param clazz
	* @param list
	* @param excludes
	* @return String
	* @Description: 将对象的List转换为json格式的字符串,如:[{"name":"张三"},{"name":"李四"}],对list中的对象设置不输出的字段
	*/
	public static <T> String printJSONArray(Class<T> clazz ,List<T> list, String[] excludes){
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(clazz);
		jc.setExcludes(excludes);
		JSONArray ja = JSONArray.fromObject(list, jc);
		return ja.toString();
	}
	
	/** 
	* @Title: paginationJSON  
	* @param object
	* @param count
	* @return String
	* @Description: 生成bootstrap-table用于构造表格所用的json,带分页,object传入的是相关List<Object> list
	*/
	public static String paginationJSONStr(Object list, long count){
		String result ="{\"total\":\"2\",\"page\": \"1\","+ "\"records\":\"" + count + "\",\"rows\":";
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(list.getClass());
		JSONArray ja = JSONArray.fromObject(list, jc);
		result += ja.toString();
		result = result + "}";
		return result;
	}
	
	/** 
	* @Title: paginationJSONStr  
	* @param clazz
	* @param list
	* @param excludes
	* @param count
	* @return String
	* @Description: 生成bootstrap-table用于构造表格所用的json,带分页,list传入的是相关List<T> list,excludes对list中的对象设置不输出的字段
	*/
	public static <T> String paginationJSONStr(Class<T> clazz ,List<T> list, String[] excludes, int count){
		String result = "{\"total\":\"" + count + "\",\"rows\":";
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(clazz);
		jc.setExcludes(excludes);
		JSONArray ja = JSONArray.fromObject(list, jc);
		result += ja.toString();
		result = result + "}";
		return result;
	}
	
	
	/** 
	* @Title: mobileListJSON  
	* @param object
	* @param count
	* @return String
	* @throws UnsupportedEncodingException 
	* @Description: 移动端返回列表的特定json格式
	*/
	public static String mobileListJSON(Object object, int count) throws UnsupportedEncodingException{
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(object.getClass());
		//去掉与数据权限相关的字段
		String[] excludes = {"majorPermission","deptPermission","selfPermission"};
		jc.setExcludes(excludes);
		JSONArray ja = JSONArray.fromObject(object, jc);
		String result = "{\"MD5\":\""+MD5.eccrypt(ja.toString())+"\",\"total\":\"" + count + "\",\"rows\":";
		result += ja.toString();
		result = result + "}";
		result = URLEncoder.encode(result, "utf-8");
		return result;
	}
	
	/** 
	* @Title: mobileListJSON  
	* @param object
	* @param count
	* @return String
	* @throws UnsupportedEncodingException 
	* @Description: 移动端返回列表的特定json格式,需要传入不需要的字段定义 excludes
	*/
	public static String mobileListJSON(Object object, int count, String[] excludes) throws UnsupportedEncodingException{
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(object.getClass());
		jc.setExcludes(excludes);
		JSONArray ja = JSONArray.fromObject(object, jc);
		String result = "{\"MD5\":\""+MD5.eccrypt(ja.toString())+"\",\"total\":\"" + count + "\",\"rows\":";
		result += ja.toString();
		result = result + "}";
		result = URLEncoder.encode(result, "utf-8");
		return result;
	}
	
	/** 
	* @Title: mobileObjectJSON  
	* @param object
	* @return String
	* @throws UnsupportedEncodingException 
	* @Description: 移动端返回对象的特定json格式
	*/
	public static String mobileObjectJSON(Object object) throws UnsupportedEncodingException{
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(object.getClass());
		String[] excludes = {"majorPermission","deptPermission","selfPermission"};
		jc.setExcludes(excludes);
		JSONObject jo = JSONObject.fromObject(object, jc);
		String className = object.getClass().getSimpleName();
		String result = "{\"MD5\":\""+MD5.eccrypt(jo.toString())+"\",\""+className+"\":";
		result += jo.toString();
		result = result + "}";
		result = URLEncoder.encode(result, "utf-8");
		return result;
	}
		
	/** 
	* @Title: getSearchCondition  
	* @param clazz
	* @param request
	* @return T
	* @Description: 传入查询条件所对应的类跟request,直接返回封装好的查询Form
	*/
	public  static <T> T getSearchConditionOfObject(Class<T> clazz, HttpServletRequest request) throws Exception{
		String limit = request.getParameter("pageSize");
		String offset = request.getParameter("pageNumber");
		if (StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		String search = request.getParameter("search");
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(clazz);
		JSONObject js = JSONObject.fromObject(search);
		@SuppressWarnings("unchecked")
		T t = (T) JSONSerializer.toJava(js, jc);
        Method m1 = clazz.getDeclaredMethod("setOffset", String.class);
        m1.invoke(t, offset);
        Method m2 = clazz.getDeclaredMethod("setLimit", String.class);
        m2.invoke(t, limit);
		return t;
	}
	
	public static Object JSONToObject(String jsonStr){
		JSONObject obj = JSONObject.fromObject(jsonStr);//将json字符串转换为json对象
		return obj;
	}
	
	/** 
	* @Title: JSONToObject  
	* @param clazz
	* @param jsonStr
	* @return T
	* @Description: 将json字符串转换为对象
	*/
	public static <T> T JSONToObject(Class<T> clazz, String jsonStr){
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(clazz);
		JSONObject obj= JSONObject.fromObject(jsonStr);
		@SuppressWarnings("unchecked")
		T t = (T) JSONSerializer.toJava(obj, jc);
		return t;
	}
	
	public static <T> T JSONArrayToObject(Class<T> clazz, String jsonStr){
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(clazz);
		JSONArray obj= JSONArray.fromObject(jsonStr);
		@SuppressWarnings("unchecked")
		List<T> tList = (List<T>) JSONSerializer.toJava(obj, jc);
		return tList.get(0);
	}
	
	public  static <T> Query<T> getSearchCondition(Class<T> clazz, HttpServletRequest request) throws Exception{
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("search");
		T t = JSON.parseObject(search, clazz);
        Query<T> query = new Query<T>(t);
        query.setPage(page);
        query.setRows(rows);
		return query;
	}
}

