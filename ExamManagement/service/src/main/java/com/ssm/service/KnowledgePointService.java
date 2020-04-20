package com.ssm.service;

import com.ssm.entity.KnowledgePoint;

import java.util.List;

public interface KnowledgePointService {

    List<KnowledgePoint> getKnowledgePoints(int pageNum,int pageSize);

    List<KnowledgePoint> getKnowledgePointsByFieldId(int fieldId);

    void addKnowledge(KnowledgePoint knowledgePoint);

    List<KnowledgePoint> getAll();
}
