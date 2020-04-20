package com.ssm.controller;

import com.google.gson.Gson;
import com.ssm.entity.AnswerSheet;
import com.ssm.entity.AnswerSheetItem;
import com.ssm.entity.ExamPaper;
import com.ssm.entity.Message;
import com.ssm.service.ExamPaperService;
import com.ssm.service.ExamService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ExamAction {

	@Autowired
	private ExamPaperService examPaperService;

	@Autowired
	private ExamService examService;

	@GetMapping("/api/exampaper/{id}")
	public ExamPaper getExamPaper(@PathVariable("id") int id, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		ExamPaper paper = examPaperService.getExamPaperById(id);
		System.out.println("paper = " + paper);
		return paper;
	}

	
	@RequestMapping(value = "/api/answersheet")
	public Message submitAnswerSheet(@RequestBody AnswerSheet answerSheet){
		List<AnswerSheetItem> itemList = answerSheet.getAnswerSheetItems();
		//全部是客观题，则状态更改为已阅卷
		int approved = 3;
		for(AnswerSheetItem item : itemList){
			if(item.getQuestionTypeId() != 1 && item.getQuestionTypeId() != 2 && item.getQuestionTypeId() != 3){
				approved = 2;
				break;
			}
		}
		Gson gson = new Gson();
		examService.updateUserExamHist(answerSheet, gson.toJson(answerSheet),approved);
		return new Message();
	}
	
}
