package com.ducetech.pms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.DateUtil;
import com.ducetech.util.DuceTechUtils;

@Controller
@RequestMapping("/file")
public class UploadController {
	
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	
	
	@RequestMapping(value = "/pager", method = RequestMethod.GET)
	public String testPager(Model model, HttpServletRequest request) {
		return "/pms/upload/upload-test";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload/{uuid}", method = RequestMethod.POST)
	public void upload(@PathVariable String uuid , HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("uuid："+uuid);
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		String path;
		String fileName = "";
		if(os.startsWith("Win")) {
			path = "C:\\pms\\files\\";
		}else{
			path = DuceTechUtils.readProp("filesUploadPath");
		}
		path = path +File.separator + DateUtil.dateToStringCode(new Date()) + File.separator;
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		// 这里我用到了jar包
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					fileName = file.getOriginalFilename();
					// 下面的加的日期是为了防止上传的名字一样
					path = path + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
					File localFile = new File(path);
					file.transferTo(localFile);
				}
			}
		}
		List<Map<String, String>> list = null;
		Map<String, String> map = new HashMap<String, String>();
		if(myRedisTemplate.getItem(uuid)==null){   //没有数据，向redis中插入一条数据
			list = new ArrayList<Map<String,String>>();
			map.put(fileName, path);
			list.add(map);
			myRedisTemplate.setItem(uuid, list);
		}else {   				 //如果有数据，取出对应uuid数据的集合，在集合里增加一条数据
			list =  (List<Map<String, String>>) myRedisTemplate.getItem(uuid);
			map.put(fileName, path);
			list.add(map);
			myRedisTemplate.setItem(uuid, list);
		}
		myRedisTemplate.expire(uuid);   //设置uuid过期时间 ，过期时间为30分钟
		response.getWriter().write("{"+path+","+fileName+"}");
	}
	
	@RequestMapping(value = "/deleteFile/{path}" , method = RequestMethod.POST)
	public void deleteFile(String path, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		response.getWriter().write("{\"msg\":\"删除成功\"}");
	}

	@RequestMapping("/download")
	public void download(String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ fileName);
		try {
			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath()
					+ "download";// 这个download目录为啥建立在classes下的
			InputStream inputStream = new FileInputStream(new File(path
					+ File.separator + fileName));
			
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
	}
}