package com.ducetech.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

/** 
 * @ClassName: TestPost  
 * @author chensf
 * @date 2015年12月3日 上午11:15:48 
 * @Description: 测试POST
 */
public class TestPost {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		String result = sendPost("http://192.168.1.120:8080/mainten/mobile/mobileLogin","username=wcn&password=123456");
//		String result = sendPost("http://192.168.1.106:8080/mainten/mobile/queryExecuteForm","userId=1&locationCode=adsa");
//		String result = sendPost("http://192.168.1.106:8080/mainten/mobile/getExecuteForm","executeFormId=189");
//		String result = sendPost("http://127.0.0.1:8080/mainten/mobile/getExecuteForm","executeFormId=1");
//		String result = sendPost("http://123.56.137.112:8080/mainten/mobile/getExecuteForm","executeFormId=2");
//		String result = sendPost("http://192.168.1.16:8080/mainten/mobile/getExecuteForm","executeFormId=353");
//		String result = sendPost("http://172.16.8.165:8080/mainten/mobile/getExecuteForm","executeFormId=");
//		String executeForm = "{\"deptName\":\"维修四项目部\",\"assignTime\":\"2016-01-11 14:00\",\"principalPersonName\":\"金艳凤\",\"locationId\":\"289\",\"handBookId\":\"\",\"locationName\":\"清华东路西口\",\"executePersonName\":\"\",\"type\":\"手持端\",\"taskCode\":\"141601070032\",\"executeTimeStart\":\"2016-01-12 17:16:19\",\"workHour\":\"0\",\"deviceName\":\"自动检票机\",\"lineName\":\"15号线\",\"repair\":\"否\",\"stationName\":\"清华东路西口\",\"lineId\":\"9\",\"executePersonId\":\"\",\"taskId\":\"236\",\"executeDate\":\"2016-01-04\",\"isComplete\":\"未完成\",\"itemName\":\"乘客显示器功能状态测试\",\"abnormal\":\"\",\"filePath\":\"\",\"itemType\":\"旬计划\",\"repairContent\":\"\",\"executeTimeEnd\":\"\",\"executeFormId\":\"191\",\"majorId\":\"3\",\"potentialHazardContent\":\"\",\"locationCode\":\"AG1-qhdlxk\",\"repairDeptId\":\"57\",\"executeComment\":\"\",\"teamType\":\"\",\"isConfirm\":\"未完成\",\"majorName\":\"AFC\",\"comment\":\"\",\"handBook\":null,\"potentialHazard\":\"否\",\"deptId\":\"14\"}";
//		String executeForm = "{\"deptName\":\"维修四项目部\",\"assignTime\":\"2016-01-08 14:00\",\"principalPersonName\":\"金艳凤\",\"locationId\":\"291\",\"handBookId\":\"1\",\"locationName\":\"清华东路西口\",\"executePersonName\":\"\",\"type\":\"手持端\",\"taskCode\":\"141601070030\",\"executeTimeStart\":\"2016-01-12 17:29:06\",\"workHour\":\"0\",\"deviceName\":\"自动检票机\",\"lineName\":\"15号线\",\"repair\":\"否\",\"stationName\":\"清华东路西口\",\"lineId\":\"9\",\"executePersonId\":\"1\",\"taskId\":\"234\",\"executeDate\":\"2016-01-04\",\"isComplete\":\"未完成\",\"itemName\":\"电源模块清扫、状态测试\",\"abnormal\":\"\",\"filePath\":\"\",\"itemType\":\"旬计划\",\"repairContent\":\"\",\"executeTimeEnd\":\"\",\"executeFormId\":\"187\",\"majorId\":\"3\",\"potentialHazardContent\":\"\",\"locationCode\":\"AG3-qhdlxk\",\"repairDeptId\":\"57\",\"executeComment\":\"\",\"teamType\":\"\",\"isConfirm\":\"未完成\",\"majorName\":\"AFC\",\"comment\":\"\",\"handBook\":{\"createTime\":\"2016-01-05 11:15:32\",\"handBookCategory\":\"1\",\"createById\":\"\",\"handBookId\":\"1\",\"createBy\":\"\",\"handBookName\":\"AFC电源设备状态检查记录表\",\"handBookReserveds\":[{\"createTime\":\"\",\"handBookReservedId\":\"1\",\"width\":\"12\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"\",\"type\":\"base\",\"createBy\":\"\",\"parentReservedId\":\"\",\"isDelete\":\"\",\"level\":\"1\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"AFC配电箱\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"2\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"zldy\",\"type\":\"select\",\"createBy\":\"\",\"parentReservedId\":\"1\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"主路电源工作情况\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"T\",\"reservedId\":\"2\"},{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"F\",\"reservedId\":\"2\"}]},{\"createTime\":\"\",\"handBookReservedId\":\"3\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"bldy\",\"type\":\"select\",\"createBy\":\"\",\"parentReservedId\":\"1\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"备路电源工作情况\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"T\",\"reservedId\":\"3\"},{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"F\",\"reservedId\":\"3\"}]},{\"createTime\":\"\",\"handBookReservedId\":\"4\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"trdy\",\"type\":\"select\",\"createBy\":\"\",\"parentReservedId\":\"1\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"投入电源为\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"主路电源\",\"reservedId\":\"4\"},{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"备路电源\",\"reservedId\":\"4\"}]},{\"createTime\":\"\",\"handBookReservedId\":\"5\",\"width\":\"12\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"\",\"type\":\"base\",\"createBy\":\"\",\"parentReservedId\":\"\",\"isDelete\":\"\",\"level\":\"1\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"UPS主面板\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"6\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"scdy\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"5\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"输出电压(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"7\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"scpl\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"5\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"输出频率(HZ)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"8\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"upssfzc\",\"type\":\"select\",\"createBy\":\"\",\"parentReservedId\":\"5\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"UPS是否正常\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"T\",\"reservedId\":\"8\"},{\"text\":\"\",\"reservedValueId\":\"\",\"orderOn\":\"\",\"value\":\"F\",\"reservedId\":\"8\"}]},{\"createTime\":\"\",\"handBookReservedId\":\"9\",\"width\":\"12\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"\",\"type\":\"base\",\"createBy\":\"\",\"parentReservedId\":\"\",\"isDelete\":\"\",\"level\":\"1\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"UPS输入电压参数(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"10\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"uav\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"9\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"Ua(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"11\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"ubv\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"9\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"Ub(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"12\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"ucv\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"9\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"Uc(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"13\",\"width\":\"12\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"\",\"type\":\"base\",\"createBy\":\"\",\"parentReservedId\":\"\",\"isDelete\":\"\",\"level\":\"1\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"UPS输出电压参数(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"14\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"uv\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"13\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"U(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"15\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"fhz\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"13\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"f(Hz)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"16\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"fz\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"13\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"负载(%)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"17\",\"width\":\"12\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"\",\"type\":\"base\",\"createBy\":\"\",\"parentReservedId\":\"\",\"isDelete\":\"\",\"level\":\"1\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"UPS电池电压\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"18\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"v\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"17\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"(V)\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"19\",\"width\":\"12\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"\",\"type\":\"base\",\"createBy\":\"\",\"parentReservedId\":\"\",\"isDelete\":\"\",\"level\":\"1\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"UPS机内温度\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]},{\"createTime\":\"\",\"handBookReservedId\":\"20\",\"width\":\"2\",\"createById\":\"\",\"handBookId\":\"1\",\"inputName\":\"ssd\",\"type\":\"text\",\"createBy\":\"\",\"parentReservedId\":\"19\",\"isDelete\":\"\",\"level\":\"2\",\"parentReservedName\":\"\",\"orderOn\":\"1\",\"handbook\":[],\"labelName\":\"()\",\"savedValue\":\"\",\"note\":\"\",\"fieldValues\":[]}],\"isDelete\":\"\",\"handBookCode\":\"\",\"comment\":\"\"},\"potentialHazard\":\"否\",\"deptId\":\"14\"}";
//		String executeForm = "{\"abnormal\":\"\",\"assignTime\":\"2016-01-13 16:00\",\"comment\":\"\",\"deptId\":\"14\",\"deptName\":\"维修四项目部\",\"deviceName\":\"自动检票机\",\"executeComment\":\"\",\"executeDate\":\"2016-01-12\",\"executeFormId\":\"141\",\"executeFormIds\":\"\",\"executePersonId\":\"\",\"executePersonName\":\"\",\"executeTimeStart\":\"2016-02-23 11:37:13\",\"filePath\":\"\",\"handBookId\":\"\",\"isComplete\":\"未完成\",\"isConfirm\":\"未完成\",\"itemName\":\"读卡器连接线缆检查、状态测试\",\"itemType\":\"旬计划\",\"lineId\":\"9\",\"lineName\":\"15号线\",\"locationCode\":\"AG2-gz\",\"locationId\":\"75\",\"locationName\":\"关庄\",\"majorId\":\"3\",\"majorName\":\"AFC\",\"potentialHazard\":\"否\",\"potentialHazardContent\":\"\",\"principalPersonName\":\"王成南\",\"repair\":\"否\",\"repairContent\":\"\",\"repairDeptId\":\"57\",\"stationName\":\"关庄\",\"taskCode\":\"141601120004\",\"taskId\":\"188\",\"teamType\":\"\",\"type\":\"手持端\",\"workHour\":\"012\"}";
		String executeForm = "a是";
		
		
		
		String MD5Str = MD5.eccrypt(executeForm);
		System.err.println(MD5Str);
		System.err.println(URLEncoder.encode(MD5Str,"utf-8"));
		executeForm = URLEncoder.encode(executeForm,"utf-8");
		String result = sendPost("http://192.168.1.106:8080/mainten/mobile/updateExecuteForm","MD5="+MD5Str+"&"+"executeForm="+executeForm);
//		String result = sendPost("http://123.56.137.112:8080/mainten/mobile/updateExecuteForm","MD5="+MD5Str+"&"+"executeForm="+executeForm);
//		String result = sendPost("http://192.168.1.109:8080/mainten/mobile/commitExecuteForm","MD5="+MD5Str+"&"+"executeForm="+executeForm);
//		String result = sendPost("http://192.168.1.106:8080/mainten/mobile/deviceSpareAction","method=update&executeFormId=194&deviceInfoId=34&usedNum=98");
//		String result = sendPost("http://192.168.1.109:8080/mainten/mobile/deviceSpareAction","method=update&executeFormId=121&deviceInfoId=29&usedNum=987");
//		String result = sendPost("http://192.168.1.109:8080/mainten/mobile/deviceSpareAction","method=add&executeFormId=121&deviceInfoId=29&usedNum=987");
//		String result = sendPost("http://192.168.1.106:8080/mainten/mobile/getDeviceSpareList","type=selected&executeFormId=194&pageSize=10&pageNumber=0&search={\"systemId\":\"\",\"deviceMajorId\":\"3\"}");
	//	String result = sendPost("http://192.168.1.106:8080/mainaaaten/mobile/getDeviceSpareList","method=已添加&executeFormId=239");
	//	String result = sendPost("http://172.16.8.165:8080/mainten/mobile/getDeviceSpareList","type=selected&executeFormId=239&pageSize=10&pageNumber=0&search={\"systemId\":\"1\",\"deviceMajorId\":\"1\"}");
	System.out.println(URLDecoder.decode(result, "utf-8"));
	}
	public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
            // 定义待写入数据的内容类型，POST设置为application/x-www-form-urlencoded类型  
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    } 
	
	
	public static void sendPost(String urlStr){  
        try{  
            // Configure and open a connection to the site you will send the request  
            URL url = new URL(urlStr);  
            URLConnection urlConnection = url.openConnection();  
            // 设置doOutput属性为true表示将使用此urlConnection写入数据  
            urlConnection.setDoOutput(true);  
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型  
            urlConnection.setRequestProperty("content-type", "text/html;charset=UTF-8");
            // 得到请求的输出流对象  
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());  
            // 把数据写入请求的Body  
            out.write("executeFormId=220");  
            out.flush();  
            out.close();  
              
            // 从服务器读取响应  
            InputStream inputStream = urlConnection.getInputStream();  
            String encoding = urlConnection.getContentEncoding();  
            String body = IOUtils.toString(inputStream, encoding);  
            System.out.println(body);  
        }catch(IOException e){  
        	e.printStackTrace();
        }  
    }  
}
