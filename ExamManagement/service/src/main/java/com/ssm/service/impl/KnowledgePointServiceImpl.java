package com.ssm.service.impl;

import com.ssm.dao.QuestionMapper;
import com.ssm.entity.KnowledgePoint;
import com.ssm.service.KnowledgePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgePointServiceImpl implements KnowledgePointService {

    @Autowired
    private QuestionMapper questionMapper;


    @Override
    public List<KnowledgePoint> getKnowledgePoints(int pageNum, int pageSize) {
        return questionMapper.getKnowledgePoints(pageNum, pageSize);
    }

    @Override
    public List<KnowledgePoint> getKnowledgePointsByFieldId(int fieldId) {
        return questionMapper.getKnowledgePointsByFieldId(fieldId);
    }

    @Override
    public void addKnowledge(KnowledgePoint knowledgePoint) {
        questionMapper.addKnowledge(knowledgePoint);
    }

    @Override
    public List<KnowledgePoint> getAll() {
        return questionMapper.getKnowledgePointsAll();
    }
}
