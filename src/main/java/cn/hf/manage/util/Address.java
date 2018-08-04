package cn.hf.manage.util;

import java.net.InetAddress;

/**
 * 说明：获取本地ip和地址
 * @author XiJie
 * @version 1.0
 * @date 2017年10月14日
 */
public class Address {

	
	/**
	 * 说明：获取IP
	 * @return
	 * @author XiJie
	 * @time：2017年10月14日 下午3:22:04
	 */
	public static String getIp(){
		InetAddress addr;
		String ip="";
		try {
			addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString();//获得本机IP
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	/**
	 * 说明：获取机名
	 * @return
	 * @author XiJie
	 * @time：2017年10月14日 下午3:22:12
	 */
	public static String getAddress(){
		InetAddress addr;
		String address="";
		try {
			addr = InetAddress.getLocalHost();
			address=addr.getHostName().toString();//获得本机名称 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}
	
}
