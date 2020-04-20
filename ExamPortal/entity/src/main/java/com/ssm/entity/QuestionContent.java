package com.ssm.entity;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class QuestionContent {

	private String title;
	private String titleImg = "";
	private LinkedHashMap<String, String> choiceList;
	private LinkedHashMap<String, String> choiceImgList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public LinkedHashMap<String, String> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(LinkedHashMap<String, String> choiceList) {
		this.choiceList = choiceList;
	}

	public LinkedHashMap<String, String> getChoiceImgList() {
		return choiceImgList;
	}

	public void setChoiceImgList(LinkedHashMap<String, String> choiceImgList) {
		this.choiceImgList = choiceImgList;
	}

}
