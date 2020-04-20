package com.ssm.service;

import com.ssm.entity.AnswerSheet;
import com.ssm.entity.Exam;
import com.ssm.entity.ExamHistory;

import java.util.HashMap;
import java.util.List;

public interface ExamService {

    List<Exam> getExamList(Integer pageNum, Integer pageSize,Integer typeId);

    List<Exam> getExamAll();

    void addExam(Exam exam);

    int getExamCount();

    int getModelTestCount();

    /**
     * 根据考试id获取考试历史记录
     * @param examId
     * @param searchStr
     * @param order
     * @param limit
     * @return
     */
    public List<ExamHistory> getUserExamHistListByExamId(int examId, String searchStr, String order, int limit);

    /**
     * 审核用户考试申请
     * @param histId
     * @param approved
     */
    public void changeUserExamHistStatus(int histId, int approved);

    /**
     * 获取用户考试历史列表（所有）
     * @param approved
     * @return
     */
    public List<ExamHistory> getUserExamHistList(int ... approved);

    /**
     * 根据考试ID删除考试
     * @param examId
     */
    public void deleteExamById(int examId);

    /**
     * 更新答题卡及得分
     * @param answerSheet
     * @param answerSheetStr
     */
    public void updateUserExamHist(AnswerSheet answerSheet, String answerSheetStr, int approved);

    /**
     * 更新考试的审核状态
     * @param examId
     * @param approved
     */
    public void changeExamStatus(int examId, int approved);

    /**
     * 根据考试历史id获取考试历史
     * @param histId
     * @return
     */
    public ExamHistory getUserExamHistListByHistId(int histId);

    public int getUserExamHistListByExamIdCount(int examId);

}
