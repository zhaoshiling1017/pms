package com.ducetech.util;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import com.ducetech.framework.model.Query;

public class PagerQueryUtil {

	public  static <T> Query<T> getPagerQuery(Class<T> clazz, HttpServletRequest request) throws Exception{
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("search");
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(clazz);
		JSONObject js = JSONObject.fromObject(search);
		@SuppressWarnings("unchecked")
		T t = (T) JSONSerializer.toJava(js, jc);
        Query<T> query = new Query<T>(t);
        query.setPage(page);
        query.setRows(rows);
		return query;
	}
}
