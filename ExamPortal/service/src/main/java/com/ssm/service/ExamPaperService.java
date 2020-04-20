package com.ssm.service;

import com.ssm.entity.ExamPaper;

public interface ExamPaperService {
	
	/**
	 * 获取一张试卷
	 * @param examPaperId
	 * @return
	 */
	public ExamPaper getExamPaperById(int examPaperId);
	
	
}
