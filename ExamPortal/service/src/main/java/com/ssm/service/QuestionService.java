package com.ssm.service;

import com.github.pagehelper.Page;
import com.ssm.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QuestionService {


    /*获取所有field*/
    List<Field> getAllField( );

    /**
     * 根据fieldId,pointId,typeId分组统计试题数量
     * @param fieldId
     * @return
     */
    public Map<Integer, Map<Integer, QuestionStatistic>> getTypeQuestionStaticByFieldId(int fieldId);

    /**
     * 获取Field下的知识点
     * @return
     */
    public Map<Integer,KnowledgePoint> getKnowledgePointByFieldId(Integer fieldId);

    /**
     * 根据试题类型和知识点获取试题
     *
     * @param typeId
     * @param pointId
     * @return
     */
    List<QuestionQueryResult> getQuestionAnalysisListByPointIdAndTypeId(@Param("typeId") int typeId,
                                                                        @Param("pointId") int pointId);

    /**
     * 按专业获取试题
     * @param fieldId
     * @param
     * @return
     */
    public Map<Integer,Map<Integer,List<QuestionQueryResult>>> getQuestionMapByFieldId(int fieldId);

    public Map<Integer, QuestionType> getQuestionTypeMap();

    /**
     * 根据fieldId分组统计试题数量
     * @param fieldId
     * @return
     */
    public Map<Integer,QuestionStatistic> getQuestionStaticByFieldId(int fieldId);

    /**
     * 根据试题id获取试题清单
     * @param idList
     * @return
     */
    List<QuestionQueryResult> getQuestionAnalysisListByIdList(List<Integer> idList);
}
