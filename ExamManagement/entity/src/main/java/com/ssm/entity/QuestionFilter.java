package com.ssm.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
@Data
public class QuestionFilter implements Serializable {

	private static final long serialVersionUID = -8784942836284858739L;

	private int fieldId;

	private int knowledge;

	private int questionType;

	private int tag;

	private String searchParam;

	private int pageNum;

	public int getPageNum(){
		return pageNum;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

}
