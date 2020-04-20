package com.ssm.service.impl;


import com.github.pagehelper.Page;
import com.ssm.dao.ExamMapper;
import com.ssm.dao.ExamPaperMapper;
import com.ssm.entity.Exam;
import com.ssm.entity.ExamHistory;
import com.ssm.entity.ExamPaper;
import com.ssm.entity.StringUtil;
import com.ssm.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private ExamPaperMapper examPaperMapper;
	@Override
	public ExamHistory getUserExamHistBySeriNo(String seriNo, int approved) {
		
		return examMapper.getUserExamHistBySeriNo(seriNo,approved);
	}
	@Override
	public Exam getExamById(int examId) {
		
		return examMapper.getExamById(examId);
	}
	@Override
	public ExamHistory getUserExamHistByUserIdAndExamId(int userId, int examId, int ... approved) {
		if(approved != null && approved.length == 0)
			approved = null;
		return examMapper.getUserExamHistByUserIdAndExamId(userId, examId, approved);
	}
	@Override
	public int addUserExamHist(int userId,int examId,int examPaperId,int approved) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		try {
			ExamPaper examPaper = examPaperMapper.getExamPaperById(examPaperId);
			ExamHistory history = new ExamHistory();
			history.setExamId(examId);
			history.setExamPaperId(examPaperId);
			history.setContent(examPaper.getContent());
			history.setDuration(examPaper.getDuration());
			
			history.setApproved(approved);
			Date now = new Date();
			String seriNo = sdf.format(now) + StringUtil.format(userId, 3) + StringUtil.format(examId, 3);
			history.setSeriNo(seriNo);
			
			history.setUserId(userId);
			examMapper.addUserExamHist(history);
			return history.getHistId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getCause());
		}
	}
	@Override
	public ExamHistory getUserExamHistListByHistId(int histId) {
		
		return examMapper.getUserExamHistListByHistId(histId);
	}
	@Override
	public List<Exam> getExamListToApply(int userId) {
		
		return examMapper.getExamListToApply(userId);
	}
	@Override
	public List<Exam> getExamListToStart(int userId, int ... typeIdList) {
		
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getExamListToStart(userId, typeIdList);
	}
	@Override
	public List<Exam> getExamList( int... typeIdList) {
		
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getExamList(typeIdList);
	}
	@Override
	public List<ExamHistory> getUserExamHistByUserId(int userId, int... typeIdList) {
		// TODO Auto-generated method stub
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getUserExamHistByUserId(userId, typeIdList);
	}
}
