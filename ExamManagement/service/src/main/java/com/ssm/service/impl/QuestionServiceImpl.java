package com.ssm.service.impl;

import com.ssm.dao.QuestionMapper;
import com.ssm.entity.*;
import com.ssm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;


    @Override
    public List<Question> getQuestions(int pageNum, int pageSize, QuestionFilter questionFilter) {
        return questionMapper.getQuestions(pageNum, pageSize,questionFilter.getFieldId(),questionFilter.getKnowledge(),questionFilter.getQuestionType(),questionFilter.getSearchParam());
    }

    @Override
    public List<QuestionType> getQuestionTypesAll() {
        return questionMapper.getQuestionTypesAll();
    }

    @Override
    public Question getQuestionById(int id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public List<QuestionQueryResult> getQuestionDescribeListByIdList(List<Integer> idList) {
        return questionMapper.getQuestionAnalysisListByIdList(idList);
    }

    @Override
    public Question getQuestionDetail(int questionId) {
        Question question = questionMapper.getQuestionById(questionId);
        question.setTagList(questionMapper.getQuestionTagByQuestionId(questionId));
        question.setKnowledgePoint(questionMapper.getKnowledgePointByQuestionId(questionId));
        return question;
    }

    @Override
    public List<KnowledgePoint> getKnowledgePointByFieldId(int fieldId) {
        return questionMapper.getKnowledgePointsByFieldId(fieldId);
    }

    @Override
    public void updateQuestion(Question question, List<QuestionTag> questionTagList) {
        questionMapper.updateQuestion(question);
        questionMapper.deleteQuestionPointByQuestionId(question.getId());
        for (int id : question.getPointList()) {
            questionMapper.addQuestionKnowledgePoint(question.getId(), id);
        }
        List<Integer> idList = new ArrayList<Integer>();
        for (QuestionTag t : questionTagList) {
            idList.add(t.getTagId());
        }

        if (questionTagList != null && questionTagList.size() != 0) {
            questionMapper.deleteQuestionTag(question.getId(), 0, null);
            questionMapper.addQuestionTag(questionTagList);
        } else {
            questionMapper.deleteQuestionTag(question.getId(), 0, null);
        }
    }

    @Override
    public void deleteQuestionByID(int questionId) {
        questionMapper.deleteQuestionByID(questionId);
    }

    @Override
    public void addQuestion(Question question) {
        try {
            questionMapper.addQuestion(question);
            for (Integer i : question.getPointList()) {
                questionMapper.addQuestionKnowledgePoint(question.getId(), i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Map<Integer, String> getKnowledgePointMap(Integer fieldId) {
        List<KnowledgePoint> knowledgeList = null;
        knowledgeList = questionMapper.getKnowledgePointByFieldId(fieldId);
        Map<Integer, String> knowledgeMap = new HashMap<Integer, String>();
        for(KnowledgePoint kp : knowledgeList){
            knowledgeMap.put(kp.getPointId(), kp.getPointName());
        }
        return knowledgeMap;
    }

    @Override
    public Map<Integer, String> getQuestionTypeMap() {
        List<QuestionType> typeList = questionMapper.getQuestionTypesAll();
        Map<Integer, String> typeMap = new HashMap<Integer, String>();
        for(QuestionType tp : typeList){
            typeMap.put(tp.getId(), tp.getName());
        }
        return typeMap;
    }

    @Override
    public HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> getQuestionStrutsMap(List<Integer> idList) {
        HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> hm = new HashMap<Integer, HashMap<Integer, List<QuestionStruts>>>();
        List<QuestionStruts> questionList = questionMapper.getQuestionListByPointId(idList);
        for (QuestionStruts q : questionList) {
            HashMap<Integer, List<QuestionStruts>> hashmap = new HashMap<Integer, List<QuestionStruts>>();
            List<QuestionStruts> ql = new ArrayList<QuestionStruts>();
            if (hm.containsKey(q.getPointId()))
                hashmap = hm.get(q.getPointId());
            if (hashmap.containsKey(q.getQuestionTypeId()))
                ql = hashmap.get(q.getQuestionTypeId());
            ql.add(q);
            hashmap.put(q.getQuestionTypeId(), ql);
            hm.put(q.getPointId(), hashmap);
        }
        return hm;
    }

    @Override
    public void deleteFieldByIdList(List<Integer> idList) {
        // TODO Auto-generated method stub
        questionMapper.deleteFieldByIdList(idList);
    }

    @Override
    public void deleteKnowledgePointByIdList(List<Integer> idList) {
        // TODO Auto-generated method stub
        questionMapper.deleteKnowledgePointByIdList(idList);
    }

    @Override
    public List<Question> getQuestionList(Page<Question> pageModel,QuestionFilter questionFilter) {
        return  questionMapper.getQuestionList(questionFilter,pageModel);
    }

    @Override
    public int getQuestionCount() {
        return questionMapper.getQuestionCount();
    }

    @Override
    public List<KnowledgePoint> getKnowledgePointListByFieldId(int pageNum, int pageSize, int fieldId) {
        return questionMapper.getKnowledgePointListByFieldId(pageNum, pageSize, fieldId);
    }

    @Override
    public int getKnowledgePoinCount() {
        return questionMapper.getKnowledgePoinCount();
    }

    @Override
    public int getTagCount() {
        return questionMapper.getTagCount();
    }

    @Override
    public List<PointStatistic> getPointCount(int fieldId) {
        return questionMapper.getPointCount(fieldId);
    }

}
