package com.ssm.dao;

import com.ssm.entity.AnswerSheet;
import com.ssm.entity.Exam;
import com.ssm.entity.ExamHistory;
import org.apache.ibatis.annotations.Param;
import java.util.List;


/**
 * 考试dao层
 */
public interface ExamMapper {

    /**
     * 获取试卷清单
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Exam> getExamList(@Param("pageNum")int pageNum,@Param("pageSize")int pageSize,@Param("typeId") Integer typeId);

    List<Exam> getExamAll();

    void addExam(Exam exam);

    /**
     * 获取考试总数
     * @return
     */
    int getExamCount();

    /**
     * 获取模拟考试总数
     * @return
     */
    int getModelTestCount();

    /**
     * 根据考试id获取考试历史记录
     * @param examId
     * @param searchStr
     * @param order
     * @param limit
     * @return
     */
    public List<ExamHistory> getUserExamHistListByExamId(@Param("examId") int examId, @Param("searchStr") String searchStr, @Param("order") String order, @Param("limit") int limit);

    /**
     * 审核用户考试申请
     * @param histId
     * @param approved
     */
    public void changeUserExamHistStatus(@Param("histId") int histId, @Param("approved") int approved);

    /**
     * 获取考试历史记录列表（所有）
     * @param approvedType
     * @return
     */
    public List<ExamHistory> getUserExamHistList(@Param("array") int[] approvedType);

    public void deleteExamById(int examId);

    /**
     * 更新答题卡及得分
     * @param answerSheet
     * @param answerSheetStr
     */
    public void updateUserExamHist(@Param("answerSheet") AnswerSheet answerSheet, @Param("answerSheetStr") String answerSheetStr, @Param("approved") int approved);

    /**
     * 更新考试的审核状态
     * @param examId
     * @param approved
     */
    public void changeExamStatus(@Param("examId") int examId, @Param("approved") int approved);

    /**
     * 根据考试历史id获取考试历史
     * @param histId
     * @return
     */
    public ExamHistory getUserExamHistListByHistId(int histId);

    public int getUserExamHistListByExamIdCount(int examId);
}
