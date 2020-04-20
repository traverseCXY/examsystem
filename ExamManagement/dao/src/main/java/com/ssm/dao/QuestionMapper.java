package com.ssm.dao;

import com.ssm.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*试题、通用数据dao层*/
public interface QuestionMapper {

	/**
	 * 分页获取所有标签
	 *
	 * @return
	 */
	public List<Tag> getTags(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

	/*获取所有标签*/
	public List<Tag> getTagsAll();

	/**
	 * 增加一个标签
	 *
	 * @param tag
	 */
	public void addTag(Tag tag);

	/*删除一个标签*/
	public void deleteTagById(Integer tagId);

	/*添加一个专业词库*/
	public void addField(Field field);

	/**
	 * 分页获取所有专业词库
	 *
	 * @return
	 */
	public List<Field> getFields(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

	/*获取所有专业词库*/
	public List<Field> getFieldsAll();

	/**
	 * 分页获取所有专业知识
	 *
	 * @return
	 */
	public List<KnowledgePoint> getKnowledgePoints(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

	/*获取所有专业知识*/
	public List<KnowledgePoint> getKnowledgePointsAll();

	/*根据专业题库获取专业知识*/
	public List<KnowledgePoint> getKnowledgePointsByFieldId(int fieldId);

	/*添加一个专业知识*/
	public void addKnowledge(KnowledgePoint knowledgePoint);

	/*分页获取所有试题*/
	public List<Question> getQuestions(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,Integer fieldId,Integer knowledge,Integer questionType,String searchParam);

	/*获取所有试题类型*/
	public List<QuestionType> getQuestionTypesAll();

	/*根据ID获取试题*/
	public Question getQuestionById(int id);

	List<QuestionQueryResult> getQuestionAnalysisListByIdList(List<Integer> idList);

	/*根据试题ID获取标签*/
	List<QuestionTag> getQuestionTagByQuestionId(int questionId);

	/*根据试题ID获取知识分类*/
	List<KnowledgePoint> getKnowledgePointByQuestionId(int questionId);

	/*根据专业题库Id获取知识分类*/
	List<KnowledgePoint> getKnowledgePointByFieldId(int fieldId);

	/*修改试题*/
	void updateQuestion(Question question);

	void deleteQuestionPointByQuestionId(int questionId);

	void addQuestionKnowledgePoint(int questionId, int pointId);

	public void deleteQuestionTag(@Param("questionId") int questionId, @Param("userId") int userId, @Param("array") List<Integer> array);

	public void addQuestionTag(@Param("array") List<QuestionTag> array);

	/*根据试题ID删除该试题*/
	void deleteQuestionByID(int questionId);

	/*添加试题*/
	void addQuestion(Question question);

	List<QuestionStruts> getQuestionListByPointId(List<Integer> idList);

	public void deleteFieldByIdList(@Param("array") List<Integer> idList);

	public void deleteKnowledgePointByIdList(@Param("array") List<Integer> idList);

	public List<Question> getQuestionList(@Param("filter") QuestionFilter filter,
										  @Param("page") Page<Question> page);

	int getQuestionCount();

	List<KnowledgePoint> getKnowledgePointListByFieldId(@Param("pageNum")int pageNum,@Param("pageSize")int pageSize,int fieldId);

	int getKnowledgePoinCount();

	int getTagCount();

	int getFieldCount();

	/**
	 * 获取知识点统计信息
	 * @param fieldId
	 * @return
	 */
	public List<PointStatistic> getPointCount(@Param("fieldId") int fieldId);

}
