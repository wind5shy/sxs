package com.shixiseng.recommend.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alfred Xu(wind5shy@qq.com)
 * 
 */
public class Properties {
	static final Log log = LogFactory.getLog(Properties.class);

	public static String redisUrl;
	public static int recommendByUserSize;
	public static int recommendByResumeSize;
	public static int recommendByInternSize;
	public static int recommendByInternContentSize;

	public static String getRedisUrl() {
		return redisUrl;
	}

	public static void setRedisUrl(String redisUrl) {
		Properties.redisUrl = redisUrl;
	}

	public static int getRecommendByUserSize() {
		return recommendByUserSize;
	}

	public static void setRecommendByUserSize(int recommendByUserSize) {
		Properties.recommendByUserSize = recommendByUserSize;
	}

	public static int getRecommendByResumeSize() {
		return recommendByResumeSize;
	}

	public static void setRecommendByResumeSize(int recommendByResumeSize) {
		Properties.recommendByResumeSize = recommendByResumeSize;
	}

	public static int getRecommendByInternSize() {
		return recommendByInternSize;
	}

	public static void setRecommendByInternSize(int recommendByInternSize) {
		Properties.recommendByInternSize = recommendByInternSize;
	}

	public static int getRecommendByInternContentSize() {
		return recommendByInternContentSize;
	}

	public static void setRecommendByInternContentSize(
			int recommendByInternContentSize) {
		Properties.recommendByInternContentSize = recommendByInternContentSize;
	}

}
