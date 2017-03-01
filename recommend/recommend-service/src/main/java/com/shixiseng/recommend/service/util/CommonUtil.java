package com.shixiseng.recommend.service.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Alfred Xu(wind5shy@qq.com)
 * 
 */
public class CommonUtil {
	static Log log = LogFactory.getLog(CommonUtil.class);

	public static URL isConnect(String urlStr) {
		URL url = null;
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return null;
		}
		int tryTimes = 3;
		while (counts < tryTimes) {
			try {
				url = new URL(urlStr);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				int state = con.getResponseCode();
				log.info("http responseCode:" + state);
				if (state == 200) {
					log.info("通知成功");
					break;
				}
				counts++;
			} catch (Exception ex) {
				counts++;
				urlStr = null;
				continue;
			}
		}
		if (counts == tryTimes)
			return null;
		return url;
	}
}
