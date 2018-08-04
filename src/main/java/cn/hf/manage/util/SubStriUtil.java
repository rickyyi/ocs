package cn.hf.manage.util;

public class SubStriUtil {

	
	/**
	 * 说明：截取字符串   String name = "华服地产(1000)";
	 * @param name
	 * @return
	 * @author XiJie
	 * @time：2017年10月26日 下午1:22:55
	 */
	public static String subString(String name) {
		String string = name.substring(name.length()-5, name.length()-1);
		@SuppressWarnings("unused")
		String string2 = name.substring(0, name.length()-6);
		return string;
	}
}
