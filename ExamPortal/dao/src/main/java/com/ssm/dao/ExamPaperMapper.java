package com.ssm.dao;

import com.github.pagehelper.Page;
import com.ssm.entity.ExamPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamPaperMapper {

	public List<ExamPaper> getExamPaperList(@Param("searchStr") String searchStr, @Param("page") Page<ExamPaper> page);
	
	public void insertExamPaper(ExamPaper examPaper);
	
	public ExamPaper getExamPaperById(int examPaperId);
	
	public void updateExamPaper(ExamPaper examPaper);
	
	public void deleteExamPaper(int id);
	
	public List<ExamPaper> getEnabledExamPaperList(@Param("userName") String userName, @Param("page") Page<ExamPaper> page);
}
