package com.ssm.dao;

import com.github.pagehelper.Page;
import com.ssm.entity.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface QuestionMapper {

	public List<QuestionType> getQuestionTypeList();

	public Question getQuestionByQuestionId(@Param("questionId") int questionId);

	/**
	 * 获取某一题型的试题
	 * 
	 * @param QuestionTypeId
	 * @param page
	 * @return
	 */
	public List<Question> getQuestionByTypeId(@Param("QuestionTypeId") int QuestionTypeId,
											  @Param("page") Page<Question> page);

	/**
	 * 按知识点获取试题
	 *
	 * @param idList
	 * @return
	 */
	List<QuestionStruts> getQuestionListByPointId(@Param("array") List<Integer> idList);

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
	 * 根据试题id获取试题清单
	 *
	 * @param idList
	 * @return
	 */
	List<QuestionQueryResult> getQuestionAnalysisListByIdList(@Param("array") List<Integer> idList);

	/**
	 * 获取所有的Field
	 *
	 * @return
	 */
	public List<Field> getAllField();

	/**
	 * 获取Field下的知识点
	 *
	 * 为null则获取所有知识点
	 * @return
	 */
	public List<KnowledgePoint> getKnowledgePointByFieldId(Integer fieldId);

	/**
	 * 按专业获取试题
	 *
	 * @param fieldId
	 * @return
	 */
	public List<QuestionQueryResult> getQuestionListByFieldId(@Param("fieldId") int fieldId);
	
	/**
	 * 根据fieldId,pointId分组统计试题数量
	 * @param fieldId
	 * @return
	 */
	public List<QuestionStatistic> getQuestionStaticByFieldId(int fieldId);
	
	/**
	 * 根据fieldId,pointId,typeId分组统计试题数量
	 * @param fieldId
	 * @return
	 */
	public List<QuestionStatistic> getTypeQuestionStaticByFieldId(int fieldId);

}
