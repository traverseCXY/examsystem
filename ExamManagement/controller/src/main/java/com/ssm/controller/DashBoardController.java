package com.ssm.controller;

import com.ssm.entity.*;
import com.ssm.service.ExamPaperService;
import com.ssm.service.ExamService;
import com.ssm.service.QuestionService;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*考试系统主页控制器*/
@Controller
public class DashBoardController {

    @Autowired
    private  UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private ExamService examService;

    @RequestMapping("/dashboard/baseinfo")
    public @ResponseBody List<Integer> baseInfo() {
        List<Integer> l = new ArrayList<>();
        l.add(questionService.getQuestionCount());
        l.add(examPaperService.getExamPaperCount());
        l.add(userService.getUserCountByStudent());
        return l;
    }

    @RequestMapping(value = "/dashboard/studentApprovedList", method = RequestMethod.GET)
    public @ResponseBody List<ExamHistory> studentApprovedList(Model model) {
        List<ExamHistory> histList = examService.getUserExamHistList( 0);
        return histList;
    }

    @RequestMapping(value = "/dashboard/StudentMarkList", method = RequestMethod.GET)
    public @ResponseBody List<ExamHistory> studentMarkList(Model model) {
        List<ExamHistory> histList = examService.getUserExamHistList( 2);
        return histList;
    }

    @RequestMapping(value = "/dashboard/chartinfo/{fieldId}", method = RequestMethod.GET)
    public @ResponseBody List<FieldNumber> chartInfo(Model model,@PathVariable("fieldId") int fieldId) {
        List<PointStatistic> pointStatisticList = questionService.getPointCount(fieldId);
        List<FieldNumber> l = new ArrayList<FieldNumber>();
        for(PointStatistic ps : pointStatisticList){
            FieldNumber fieldNumber = new FieldNumber();
            fieldNumber.name = ps.getPointName();
            fieldNumber.amount = ps.getAmount();
            l.add(fieldNumber);
        }

        return l;
    }


    class FieldNumber{
        private String name;
        private int amount;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAmount() {
            return amount;
        }
        public void setAmount(int amount) {
            this.amount = amount;
        }

    }


}
