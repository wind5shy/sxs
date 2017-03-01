package com.shixiseng.recommend.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.shixiseng.recommend.common.Constants;
import com.shixiseng.recommend.common.Properties;
import com.shixiseng.recommend.common.redis.JedisTemplate;
import com.shixiseng.recommend.common.redis.JedisTemplateFactory;
import com.shixiseng.recommend.service.RecommendService;

/**
 * RecommendService实现类
 * 
 * @author Alfred Xu(wind5shy@qq.com)
 */
public class RecommendServiceImpl implements RecommendService, Constants {

	private static final JedisTemplate redis = JedisTemplateFactory
			.getTemplate(Properties.redisUrl);

	private static final List<Long> checkAndConvertList(List<String> list,
			int size) {
		if (list == null) {
			throw new NullPointerException("list:" + list + " is null.");
		}
		if (list.size() < size) {
			throw new RuntimeException("list:" + list + "'s size["
					+ list.size() + "] is less than require[" + size + "]");
		}
		List<Long> l = new ArrayList<Long>(size);
		for (String s : list) {
			l.add(new Long(s));
		}
		return l;
	}

	@Override
	public List<Long> recommendInternsByUser(Long userId) {
		String key = RECOMMEND_INTERNS_BY_USER_KEY_PREFIX + userId;
		List<String> interns = redis.lrange(key, 1,
				Properties.recommendByUserSize);
		return checkAndConvertList(interns, Properties.recommendByUserSize);
	}

	@Override
	public List<Long> recommendInternsByResumeContent(String resumeContent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> recommendResumesByIntern(Long internId) {
		String key = RECOMMEND_RESUMES_BY_INTERN_KEY_PREFIX + internId;
		List<String> resumes = redis.lrange(key, 1,
				Properties.recommendByUserSize);
		return checkAndConvertList(resumes, Properties.recommendByInternSize);
	}

	@Override
	public List<Long> recommendResumesByInternContent(String internContent) {
		// TODO Auto-generated method stub
		return null;
	}
}