package com.ssm.dao;

import com.ssm.entity.ExamPaper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 试卷dao层
 */
public interface ExamPaperMapper {

    List<ExamPaper> getExamPaperList(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    ExamPaper getExamPaperById(int examPaperId);

    void updateExamPaper(ExamPaper examPaper);

    void deleteExamPaper(Integer examPaperId);

    void insertExamPaper(ExamPaper examPaper);

    List<ExamPaper> getExamPaperAll();

    List<ExamPaper> getExamPaperPageList(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,int typeId,String searParam);

    int getExamPaperCount();


}
