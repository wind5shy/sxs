package com.shixiseng.recommend.common.web;

import org.springframework.ui.ModelMap;

import java.io.Serializable;

/**
 * @author Alfred Xu(wind5shy@qq.com)
 * 
 */
public class Result extends ModelMap implements Serializable {

	private static final long serialVersionUID = 7037737047970625322L;

	private boolean success = true;
	private String message;

	public void add(String key, Object value) {
		this.addAttribute(key, value);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
