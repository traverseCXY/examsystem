package com.ssm.controller;

import com.ssm.entity.Message;
import com.ssm.entity.QuestionHistory;
import com.ssm.entity.User;
import com.ssm.entity.UserQuestionHistory;
import com.ssm.interceptor.UserInfo;
import com.ssm.service.QuestionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class PracticeActionController {

    @Autowired
    private QuestionHistoryService questionHistoryService;
    /**
     * 练习模式完成一道题
     *
     * @param qh
     * @return
     */
    @RequestMapping(value = "/student/practice-improve", method = RequestMethod.POST)
    public @ResponseBody
    Message submitPractice(@RequestBody QuestionHistory qh, HttpSession session) {
        Message msg = new Message();
        UserQuestionHistory history = new UserQuestionHistory();
        history.setQuestionId(qh.getQuestionId());
        User user =(User)session.getAttribute("user");
        history.setUserId(user.getUserId());
        history.setQuestionTypeId(qh.getQuestionTypeId());
        boolean isRight = qh.getAnswer().equals(qh.getMyAnswer()) ? true : false;
        history.setRight(isRight);
        try {
            questionHistoryService.addUserQuestionHist(history);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            msg.setResult(e.getClass().getName());
            e.printStackTrace();
        }
        return msg;
    }


}
