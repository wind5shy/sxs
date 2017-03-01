package com.shixiseng.recommend.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.shixiseng.recommend.common.web.Result;
import com.shixiseng.recommend.service.RecommendService;

/**
 * @author Alfred Xu(wind5shy@qq.com)
 * 
 */
@Controller
@RequestMapping("/recommend")
public class WebRestController extends BaseController {
	static final Log log = LogFactory.getLog(WebRestController.class);

	@Resource
	private RecommendService recommendSvc;

	@RequestMapping("user/{id}")
	public void recommendInternsByUser(HttpServletResponse response,
			@PathVariable Long id) {
		Result result = new Result();
		try {
			List<Long> interns = recommendSvc.recommendInternsByUser(id);
			result.add(INTERNS_KEY, interns);
		} catch (Exception e) {
			String message = "fail to get the recommended interns of user:"
					+ id;
			log.error(message, e);
			result.setSuccess(false);
			result.setMessage(message);
		}
		sendAjax(response, JSON.toJSONString(result));
	}

	@RequestMapping("user/resume/{id}")
	public void recommendInternsByResume(HttpServletResponse response,
			@PathVariable Long id,
			@RequestParam(required = true) String resumeContent) {
		Result result = new Result();
		try {
			List<Long> interns = recommendSvc
					.recommendInternsByResumeContent(resumeContent);
			result.add(INTERNS_KEY, interns);
		} catch (Exception e) {
			String message = "fail to get the recommended interns of resume:"
					+ id;
			log.error(message, e);
			result.setSuccess(false);
			result.setMessage(message);
		}
		sendAjax(response, JSON.toJSONString(result));
	}

	@RequestMapping("intern/online/{id}")
	public void recommendResumesByInternOnline(HttpServletResponse response,
			@PathVariable Long id,
			@RequestParam(required = true) String internContent) {
		Result result = new Result();
		try {
			List<Long> resumes = recommendSvc
					.recommendResumesByInternContent(internContent);
			result.add(RESUMES_KEY, resumes);
		} catch (Exception e) {
			String message = "fail to get the recommended resumes of intern:"
					+ id;
			log.error(message, e);
			result.setSuccess(false);
			result.setMessage(message);
		}
		sendAjax(response, JSON.toJSONString(result));
	}

	@RequestMapping("intern/offline/{id}")
	public void recommendResumesByInternOffline(HttpServletResponse response,
			@PathVariable Long id) {
		Result result = new Result();
		try {
			List<Long> resumes = recommendSvc.recommendResumesByIntern(id);
			result.add(RESUMES_KEY, resumes);
		} catch (Exception e) {
			String message = "fail to get the recommended resumes of intern:"
					+ id;
			log.error(message, e);
			result.setSuccess(false);
			result.setMessage(message);
		}
		sendAjax(response, JSON.toJSONString(result));
	}
}
