package com.ssm.service.impl;

import com.google.gson.Gson;
import com.ssm.dao.ExamPaperMapper;
import com.ssm.dao.QuestionMapper;
import com.ssm.entity.ExamPaper;
import com.ssm.entity.Paper;
import com.ssm.entity.QuestionQueryResult;
import com.ssm.entity.QuestionStruts;
import com.ssm.service.ExamPaperService;
import com.ssm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class ExamPaperServiceImpl implements ExamPaperService {

    @Autowired
    private ExamPaperMapper examPaperMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<ExamPaper> getExamPaperList(int pageNum, int pageSize) {
        return examPaperMapper.getExamPaperList(pageNum, pageSize);
    }

    @Override
    public List<ExamPaper> getExamPaperPageList(int pageNum, int pageSize, int typeId, String searParam) {
        return examPaperMapper.getExamPaperPageList(pageNum, pageSize, typeId, searParam);
    }

    @Override
    public ExamPaper getExamPaperById(int examPaperId) {
        return examPaperMapper.getExamPaperById(examPaperId);
    }

    @Override
    public void updateExamPaper(ExamPaper examPaper) {
        examPaperMapper.updateExamPaper(examPaper);
    }

    @Override
    public void deleteExamPaper(Integer examId) {
        examPaperMapper.deleteExamPaper(examId);
    }

    @Override
    public void insertExamPaper(ExamPaper examPaper) {
        examPaperMapper.insertExamPaper(examPaper);
    }

    @Override
    public void createExamPaper(HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap, HashMap<Integer, Integer> questionTypeNum, HashMap<Integer, Float> questionTypePoint, HashMap<Integer, Float> knowledgePointRate, ExamPaper examPaper) {
            // TODO Auto-generated method stub

            HashMap<Integer,String> knowledgeMap = (HashMap<Integer, String>) questionService.getKnowledgePointMap(0);
            HashMap<Integer,String> typeMap = (HashMap<Integer, String>) questionService.getQuestionTypeMap();
            Paper paper = new Paper(questionMap, questionTypeNum, questionTypePoint, knowledgePointRate, knowledgeMap, typeMap);
            try {
                paper.createPaper();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e1.getMessage());
            }

            try {
                HashMap<Integer, QuestionStruts> paperQuestionMap = paper.getPaperQuestionMap();

                Iterator<Integer> it = paperQuestionMap.keySet().iterator();
                List<Integer> idList = new ArrayList<Integer>();
                while (it.hasNext()) {
                    idList.add(it.next());
                }
                List<QuestionQueryResult> questionList = questionMapper.getQuestionAnalysisListByIdList(idList);
                for (QuestionQueryResult q : questionList) {
                    q.setQuestionPoint(questionTypePoint.get(q.getQuestionTypeId()));
                }
                Gson gson = new Gson();
                examPaper.setContent(gson.toJson(questionList));
                examPaperMapper.insertExamPaper(examPaper);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
    }

    @Override
    public List<ExamPaper> getExamPaperAll() {
        return examPaperMapper.getExamPaperAll();
    }

    @Override
    public int getExamPaperCount() {
        return examPaperMapper.getExamPaperCount();
    }

}
