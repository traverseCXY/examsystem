package com.ssm.service;

import com.ssm.entity.ExamPaper;
import com.ssm.entity.QuestionStruts;
import java.util.HashMap;
import java.util.List;

public interface ExamPaperService {

    List<ExamPaper> getExamPaperList(int pageNum,int pageSize);

    List<ExamPaper> getExamPaperPageList(int pageNum,int pageSize,int typeId,String searParam);

    ExamPaper getExamPaperById(int examPaperId);

    void updateExamPaper(ExamPaper examPaper);

    void deleteExamPaper(Integer examId);

    void insertExamPaper(ExamPaper examPaper);

    void createExamPaper(
            HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap,
            HashMap<Integer, Integer> questionTypeNum,
            HashMap<Integer, Float> questionTypePoint,
            HashMap<Integer, Float> knowledgePointRate, ExamPaper examPaper
    );

    List<ExamPaper> getExamPaperAll();

    int getExamPaperCount();


}
