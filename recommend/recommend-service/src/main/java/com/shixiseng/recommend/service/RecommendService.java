package com.shixiseng.recommend.service;

import java.util.List;

/**
 * RecommendService接口
 * 
 * @author Alfred Xu(wind5shy@qq.com)
 */
public interface RecommendService {
	List<Long> recommendInternsByUser(Long userId);

	List<Long> recommendInternsByResumeContent(String resumeContent);

	List<Long> recommendResumesByIntern(Long internId);

	List<Long> recommendResumesByInternContent(String internContent);
}