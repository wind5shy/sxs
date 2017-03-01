package com.shixiseng.recommend.common.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alfred Xu(wind5shy@qq.com)
 * 
 */
public class JedisTemplateFactory {
	static final Map<String, JedisTemplate> templates = new HashMap<String, JedisTemplate>();

	static JedisTemplate getTemplateFrom(String uriStr) {
		JedisURI uri = JedisURI.from(uriStr);
		JedisTemplate jedisTemplate = new JedisTemplate(
				JedisPoolFactory.createJedisPool(uri.getHost(), uri.getPort(),
						uri.getTimeout(), uri.getThreadCount()));
		return jedisTemplate;
	}

	public static final JedisTemplate getTemplate(String uriStr) {
		JedisTemplate t = templates.get(uriStr);
		if (t == null) {
			t = getTemplateFrom(uriStr);
			templates.put(uriStr, t);
		}
		return t;
	}
}
