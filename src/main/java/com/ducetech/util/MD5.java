package com.ducetech.util;

/** 
 * @ClassName: MD5  
 * @author chensf
 * @date 2015年12月3日 上午10:47:17 
 * @Description: MD5加密
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String eccrypt(String info) {
		byte[] digesta = null;
		try {
			// 得到一个md5的消息摘要
			MessageDigest alga = MessageDigest.getInstance("MD5");
			// 添加要进行计算摘要的信息
			//注意:getBytes()这个方法若是不加参数则默认按照所在操作系统平台的编码获得二进制码
			//     windows平台默认为GBK编码,linux平台默认UTF-8编码,此处注意编码统一问题
			alga.update(info.getBytes());
			// 得到该摘要
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将摘要转为字符串
		return byte2hex(digesta);
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/*public static void main(String args[]) throws NoSuchAlgorithmException {
		String msg = "{\"itemName\":\"车站录像设备状态检查\",\"itemCode\":\"001\"}";
		MD5 md5 = new MD5();
		System.out.println("密文是：" + md5.eccrypt(msg));
		System.out.println("明文是：" + msg);
	}*/
}
