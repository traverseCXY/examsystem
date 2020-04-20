package com.ssm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssm.entity.*;
import com.ssm.service.ExamPaperService;
import com.ssm.service.ExamService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.*;


@Controller
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private AmqpTemplate amqpTemplate;




    /**
     * 获取答题卡
     * @param histId
     * @return
     */
    @RequestMapping(value = "/get-answersheet/{histId}", method = RequestMethod.GET)
    public @ResponseBody
    AnswerSheet getAnswerSheet(@PathVariable("histId") int histId){
        ExamHistory history = examService.getUserExamHistListByHistId(histId);
        Gson gson = new Gson();
        AnswerSheet answerSheet = gson.fromJson(history.getAnswerSheet(), AnswerSheet.class);
        return answerSheet;
    }

    /**
     * 在线考试主页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/exam-list")
    public String examList(Model model, HttpServletRequest request) {
        int userId = 0;
        User user = (User) request.getSession().getAttribute("user");
        Page<Exam> page = new Page<Exam>();
        page.setPageSize(10);
        page.setPageNum(1);
        List<Exam> examListToApply = examService.getExamListToApply(user.getUserId());
        List<Exam> examListToStart = examService.getExamListToStart(user.getUserId(), 1, 2);
        List<Exam> modelTestToStart = examService.getExamList(3);
        model.addAttribute("examListToApply", examListToApply);
        model.addAttribute("examListToStart", examListToStart);
        model.addAttribute("modelTestToStart", modelTestToStart);
        model.addAttribute("userId", user.getUserId());
        return "exam";
    }

    /**
     * 模拟考试页面
     *
     * @param model
     * @param request
     * @param examId
     * @return
     */
    @RequestMapping(value = "/student/model-test-start/{examId}", method = RequestMethod.GET)
    public String modelTestStartPage(Model model, HttpServletRequest request, @PathVariable("examId") int examId) {
        User user = (User) request.getSession().getAttribute("user");
        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        int duration = 0;
        Exam exam = examService.getExamById(examId);

        if (exam.getApproved() != 1 || exam.getExpTime().before(new Date()) || exam.getExamType() != 3) {
            model.addAttribute("errorMsg", "考试未审核或当前时间不能考试或考试类型错误");
            return "error";
        }

        ExamPaper examPaper = examPaperService.getExamPaperById(exam.getExamPaperId());
        ExamHistory examHistory = examService
                .getUserExamHistByUserIdAndExamId(user.getUserId(), examId, 0, 1, 2, 3);
        int historyId = 0;
        if (examHistory == null) {
            //练习默认审核通过
            historyId = examService.addUserExamHist(user.getUserId(), examId, examPaper.getId(), 1);
        } else {
            historyId = examHistory.getHistId();
        }

        String content = examPaper.getContent();

        Gson gson = new Gson();
        duration = examPaper.getDuration();

        List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>() {
        }.getType());

        StringBuilder sb = new StringBuilder();
        for (QuestionQueryResult question : questionList) {
            QuestionAdapter adapter = new QuestionAdapter(question, strUrl);
            sb.append(adapter.getUserExamPaper());
        }
        model.addAttribute("examHistoryId", historyId);
        model.addAttribute("examId", examId);
        model.addAttribute("examPaperId", examPaper.getId());
        model.addAttribute("duration", duration * 60);
        model.addAttribute("htmlStr", sb.toString());
        //userInfo.setHistId(0);
        return "examing";
    }

    /**
     * 用户申请考试
     * @param examId
     * @return
     */
    @RequestMapping(value = "/student/exam/send-apply/{examId}", method = RequestMethod.GET)
    public @ResponseBody Message sendApply(@PathVariable("examId") int examId, HttpSession session){
        Message msg = new Message();
        User user = (User) session.getAttribute("user");
        try {
            Exam exam = this.checkExam(examId);
            //申请考试默认未审核
            examService.addUserExamHist(user.getUserId(), examId, exam.getExamPaperId(),0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            msg.setResult(e.getMessage());
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 检查用户是否可以申请该考试
     * @param examId
     * @return
     * @throws Exception
     */
    public Exam checkExam(int examId) throws Exception{
        Exam exam = examService.getExamById(examId);
        if(exam == null){
            throw new Exception("考试不存在！或已经申请过一次！");
        }
        if(exam.getApproved() != 1){
            throw new Exception("不能申请一个未审核通过的考试！");
        }

        return exam;
    }

    /**
     * 开始考试（公有考试和私有考试）页面
     *
     * @param model
     * @param request
     * @param examId
     * @return
     */
    @RequestMapping(value = "/student/exam-start/{examId}", method = RequestMethod.GET)
    public String examStartPage(Model model, HttpServletRequest request, @PathVariable("examId") int examId) {

        //TO-DO:学员开始考试时，将开始时间传到消息队列，用户更新用户开始考试的时间。如果数据库中时间不为空，则不更新
        User user = (User) request.getSession().getAttribute("user");
        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        int duration = 0;
        Exam exam = examService.getExamById(examId);

        if (exam.getApproved() != 1 || exam.getExpTime().before(new Date()) || exam.getExamType() == 3) {
            model.addAttribute("errorMsg", "考试未审核或当前时间不能考试或考试类型错误");
            return "error";
        }

        ExamHistory examHistory = examService
                .getUserExamHistByUserIdAndExamId(user.getUserId(), examId, 0, 1, 2, 3);
        Date startTime = examHistory.getStartTime() == null ? new Date() : examHistory.getStartTime();
        switch (examHistory.getApproved()) {
            case 0:
                model.addAttribute("errorMsg", "考试未审核");
                return "error";
            case 2:
                model.addAttribute("errorMsg", "已交卷，不能重复考试");
                return "error";
            case 3:
                model.addAttribute("errorMsg", "已阅卷，不能重复考试");
                return "error";
        }
        ExamPaper examPaper = examPaperService.getExamPaperById(examHistory.getExamPaperId());
        String content = examPaper.getContent();

        Gson gson = new Gson();
        duration = examPaper.getDuration();

        List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>() {
        }.getType());

        StringBuilder sb = new StringBuilder();
        for (QuestionQueryResult question : questionList) {
            QuestionAdapter adapter = new QuestionAdapter(question, strUrl);
            sb.append(adapter.getUserExamPaper());
        }
        model.addAttribute("startTime", startTime);
        model.addAttribute("examHistoryId", examHistory.getHistId());
        model.addAttribute("examId", examHistory.getExamId());
        model.addAttribute("examPaperId", examHistory.getExamPaperId());
        model.addAttribute("duration", duration * 60);
        model.addAttribute("htmlStr", sb.toString());
        return "examing";
    }

    /**
     * 考试交卷-----待完善
     * @param answerSheet
     * @return
     */
    @RequestMapping(value = "/student/exam-submit", method = RequestMethod.POST)
    public @ResponseBody
    Message finishExam(@RequestBody AnswerSheet answerSheet) {
        System.out.println("answerSheet = " + answerSheet);
        Message message = new Message();
        ObjectMapper om = new ObjectMapper();
       try {
           amqpTemplate.convertAndSend(Constants.ANSWERSHEET_DATA_QUEUE, om.writeValueAsBytes(answerSheet));
        } catch (AmqpException e) {
            e.printStackTrace();
            message.setResult("交卷失败");
            message.setMessageInfo(e.toString());
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            message.setResult("交卷失败");
            message.setMessageInfo(e.toString());
        }
        return message;
    }


    @RequestMapping(value = "addAnswerSheet4Test", method = RequestMethod.GET)
    public @ResponseBody Message addAnswerSheet4Test(Model model) throws IOException {
        Message msg = new Message();
        AnswerSheet as = new AnswerSheet();
        as.setExamPaperId(2);
        ObjectMapper om = new ObjectMapper();
        try {
            amqpTemplate.convertAndSend(Constants.ANSWERSHEET_DATA_QUEUE, om.writeValueAsBytes(as));
        }catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }

        /**
     * 交完卷返回的页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/student/finished-submit", method = RequestMethod.GET)
    public String finishedSubmitPage(Model model){
        return "finished-submit";
    }

}
