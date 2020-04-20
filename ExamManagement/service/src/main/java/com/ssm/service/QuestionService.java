package com.ssm.service;

import com.ssm.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QuestionService {

    List<Question> getQuestions(int pageNum, int pageSize, QuestionFilter questionFilter);

    List<QuestionType> getQuestionTypesAll();

    Question getQuestionById(int id);

    List<QuestionQueryResult> getQuestionDescribeListByIdList(List<Integer> idList);

    Question getQuestionDetail(int questionId);

    List<KnowledgePoint> getKnowledgePointByFieldId(int fieldId);

    void updateQuestion(Question question, List<QuestionTag> questionTagList);

    void deleteQuestionByID(int questionId);

    void addQuestion(Question question);

    Map<Integer, String> getKnowledgePointMap(Integer fieldId);

    public Map<Integer, String> getQuestionTypeMap();

    public HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> getQuestionStrutsMap(List<Integer> idList);

    void deleteFieldByIdList(List<Integer> idList);

    void deleteKnowledgePointByIdList(List<Integer> idList);

    List<Question> getQuestionList(Page<Question> pageModel,QuestionFilter questionFilter);

    int getQuestionCount();

    List<KnowledgePoint> getKnowledgePointListByFieldId(int pageNum,int pageSize,int fidldId);

    int getKnowledgePoinCount();

    int getTagCount();

    List<PointStatistic> getPointCount(int fieldId);

}
