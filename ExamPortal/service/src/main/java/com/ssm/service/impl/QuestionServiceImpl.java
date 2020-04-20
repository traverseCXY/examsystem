package com.ssm.service.impl;

import com.ssm.dao.QuestionMapper;
import com.ssm.entity.*;
import com.ssm.service.ExamService;
import com.ssm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Field> getAllField() {
        return questionMapper.getAllField();
    }

    @Override
    public Map<Integer, Map<Integer, QuestionStatistic>> getTypeQuestionStaticByFieldId(int fieldId) {
        List<QuestionStatistic> statisticList = questionMapper.getTypeQuestionStaticByFieldId(fieldId);
        Map<Integer, Map<Integer, QuestionStatistic>> map = new HashMap<Integer, Map<Integer, QuestionStatistic>>();
        for(QuestionStatistic statistic : statisticList){
            Map<Integer, QuestionStatistic> tmp = map.get(statistic.getPointId());
            if(tmp == null){
                tmp = new HashMap<Integer, QuestionStatistic>();
            }
            tmp.put(statistic.getQuestionTypeId(), statistic);
            map.put(statistic.getPointId(), tmp);
        }
        return map;
    }


    @Override
    public Map<Integer, KnowledgePoint> getKnowledgePointByFieldId(Integer fieldId) {
        Map<Integer,KnowledgePoint> map = new HashMap<Integer,KnowledgePoint>();
        List<KnowledgePoint> pointList = questionMapper.getKnowledgePointByFieldId(fieldId);
        for(KnowledgePoint point : pointList)
            map.put(point.getPointId(), point);
        return map;
    }

    @Override
    public List<QuestionQueryResult> getQuestionAnalysisListByPointIdAndTypeId(int typeId, int pointId) {
        return questionMapper.getQuestionAnalysisListByPointIdAndTypeId(typeId, pointId);
    }

    @Override
    public Map<Integer, Map<Integer, List<QuestionQueryResult>>> getQuestionMapByFieldId(int fieldId) {
        List<QuestionQueryResult> questionList = questionMapper.getQuestionListByFieldId(fieldId);
        Map<Integer,Map<Integer,List<QuestionQueryResult>>> map = new HashMap<Integer,Map<Integer,List<QuestionQueryResult>>>();
        for(QuestionQueryResult result : questionList){
            Map<Integer,List<QuestionQueryResult>> tmpMap = map.get(result.getKnowledgePointId());
            if(tmpMap == null)
                tmpMap = new HashMap<Integer,List<QuestionQueryResult>>();
            List<QuestionQueryResult> tmpList = tmpMap.get(result.getQuestionTypeId());
            if(tmpList == null)
                tmpList = new ArrayList<QuestionQueryResult>();
            tmpList.add(result);
            tmpMap.put(result.getQuestionTypeId(), tmpList);
            map.put(result.getKnowledgePointId(), tmpMap);
        }
        return map;
    }

    @Override
    public Map<Integer, QuestionType> getQuestionTypeMap() {
        Map<Integer,QuestionType> map = new HashMap<Integer,QuestionType>();
        List<QuestionType> typeList = questionMapper.getQuestionTypeList();
        for(QuestionType type : typeList)
            map.put(type.getId(), type);
        return map;
    }

    @Override
    public Map<Integer, QuestionStatistic> getQuestionStaticByFieldId(int fieldId) {
        List<QuestionStatistic> statisticList = questionMapper.getQuestionStaticByFieldId(fieldId);
        Map<Integer, QuestionStatistic> map = new HashMap<Integer, QuestionStatistic>();
        for(QuestionStatistic statistic : statisticList){
            map.put(statistic.getPointId(), statistic);
        }
        return map;
    }

    @Override
    public List<QuestionQueryResult> getQuestionAnalysisListByIdList(List<Integer> idList) {
        return questionMapper.getQuestionAnalysisListByIdList(idList);
    }

}
