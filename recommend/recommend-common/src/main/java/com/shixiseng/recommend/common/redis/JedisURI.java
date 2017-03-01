package com.shixiseng.recommend.common.redis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Protocol;

/**
 * @author Alfred Xu(wind5shy@qq.com)
 * 
 */
public class JedisURI {
	public static final int DEFAULT_PORT = Protocol.DEFAULT_PORT;
	public static final String TIMEOUT_KEY = "timeout";
	public static final int DEFAULT_TIMEOUT = Protocol.DEFAULT_TIMEOUT;
	public static final String THREAD_COUNT_KEY = "threadCount";
	public static final int DEFAULT_THREAD_COUNT = 200;

	final static Log log = LogFactory.getLog(JedisURI.class);

	final URI uri;
	final Map<String, String> params;

	/**
	 * @param uri
	 * @param params
	 */
	private JedisURI(URI uri, Map<String, String> params) {
		this.uri = uri;
		this.params = params;
	}

	public String getHost() {
		String host = uri.getHost();
		if (StringUtils.isBlank(host)) {
			String message = "host of URI:" + uri + " is null.";
			log.error(message);
			throw new NullPointerException(message);
		} else {
			return host;
		}
	}

	public int getPort() {
		int port = uri.getPort();
		if (port == -1) {
			String message = "port of URI:" + uri + " is null, use default:"
					+ DEFAULT_PORT;
			log.warn(message);
			port = DEFAULT_PORT;
		}
		return port;
	}

	public int getTimeout() {
		String timeout = params.get(TIMEOUT_KEY);
		if (StringUtils.isBlank(timeout)) {
			String message = "timeout of URI:" + uri + " is null, use default:"
					+ DEFAULT_TIMEOUT;
			log.warn(message);
			return DEFAULT_TIMEOUT;
		} else {
			return Integer.parseInt(timeout);
		}
	}

	public int getThreadCount() {
		String threadConunt = params.get(THREAD_COUNT_KEY);
		if (StringUtils.isBlank(threadConunt)) {
			String message = "thread count of URI:" + uri
					+ " is null, use default:" + DEFAULT_THREAD_COUNT;
			log.warn(message);
			return DEFAULT_THREAD_COUNT;
		} else {
			return Integer.parseInt(threadConunt);
		}
	}

	public static final JedisURI from(String uriStr) {
		URI uri = null;
		try {
			uri = new URI(uriStr);
		} catch (URISyntaxException e) {
			String message = "fail to initialize jedis URI from string:"
					+ uriStr + ".";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		Map<String, String> params = new HashMap<String, String>();
		String query = uri.getQuery();
		if (!StringUtils.isBlank(query)) {
			if (query.contains("&")) {
				String[] paramStrs = query.split("&");
				for (int i = 0; i < paramStrs.length; i++) {
					String[] ss = paramStrs[i].split("=");
					params.put(ss[0], ss[1]);
				}
			} else {
				String[] ss = query.split("=");
				params.put(ss[0], ss[1]);
			}
		}

		return new JedisURI(uri, params);
	}

}
