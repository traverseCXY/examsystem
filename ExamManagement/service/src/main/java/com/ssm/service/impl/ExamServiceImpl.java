package com.ssm.service.impl;

import com.ssm.dao.ExamMapper;
import com.ssm.entity.AnswerSheet;
import com.ssm.entity.Exam;
import com.ssm.entity.ExamHistory;
import com.ssm.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Override
    public List<Exam> getExamList(Integer pageNum, Integer pageSize,Integer typeId) {
        return examMapper.getExamList(pageNum,pageSize,typeId);
    }

    @Override
    public List<Exam> getExamAll() {
        return examMapper.getExamAll();
    }

    @Override
    public void addExam(Exam exam) {
        examMapper.addExam(exam);
    }

    @Override
    public int getExamCount() {
        return examMapper.getExamCount();
    }

    @Override
    public int getModelTestCount() {
        return examMapper.getModelTestCount();
    }

    @Override
    public List<ExamHistory> getUserExamHistListByExamId(int examId, String searchStr, String order, int limit) {
        // TODO Auto-generated method stub
        return examMapper.getUserExamHistListByExamId(examId, searchStr, order, limit);
    }

    @Override
    public void changeUserExamHistStatus(int histId, int approved) {
        examMapper.changeUserExamHistStatus(histId, approved);
    }

    @Override
    public List<ExamHistory> getUserExamHistList(int... approved) {
        if(approved.length == 0)
            approved = null;
        return examMapper.getUserExamHistList(approved);
    }

    @Override
    public void deleteExamById(int examId) {
        examMapper.deleteExamById(examId);
    }

    @Override
    public void updateUserExamHist(AnswerSheet answerSheet, String answerSheetStr, int approved) {
        examMapper.updateUserExamHist(answerSheet, answerSheetStr,  approved);
    }

    @Override
    public void changeExamStatus(int examId, int approved) {
        examMapper.changeExamStatus(examId, approved);
    }
    @Override
    public ExamHistory getUserExamHistListByHistId(int histId) {
        // TODO Auto-generated method stub
        return examMapper.getUserExamHistListByHistId(histId);
    }

    @Override
    public int getUserExamHistListByExamIdCount(int examId) {
        return examMapper.getUserExamHistListByExamIdCount(examId);
    }
}
