package com.shixiseng.recommend.common.web;

/**
 * @author Alfred Xu(wind5shy@qq.com)
 * 
 */
public class VmUtils {

	/**
	 * 获取对象类型，0字符串，1数字型
	 * 
	 * @param obj
	 * @return
	 */
	public static int objectType(Object obj) {
		if (obj instanceof String) {
			return 0;
		}
		if (obj instanceof Number) {
			return 1;
		}
		return -1;
	}

	public static boolean isSqlParam(Object obj) {
		if (obj instanceof String) {
			if (((String) obj).indexOf("[sql]") != -1) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static Object fiterSqlFlag(Object obj) {
		if (obj instanceof String) {
			if (((String) obj).indexOf("[sql]") != -1) {
				String result = ((String) obj).replace("[sql]", "");
				return result;
			}
			return obj;
		}
		return obj;

	}

}
