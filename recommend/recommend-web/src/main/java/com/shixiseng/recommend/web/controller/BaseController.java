package com.shixiseng.recommend.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.shixiseng.recommend.common.Constants;
import com.shixiseng.recommend.common.web.Result;

/**
 * 
 * @author Alfred Xu(wind5shy@qq.com)
 */
public class BaseController implements Constants {
	static final Log log = LogFactory.getLog(BaseController.class);

	public ModelAndView view(String view, Result result) {
		return new ModelAndView(view, result);
	}

	/**
	 * 往response中写入请求结果
	 * 
	 * @param response
	 * @param result
	 */
	public void sendAjax(HttpServletResponse response, String result) {
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
