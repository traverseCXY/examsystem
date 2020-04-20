package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssm.entity.*;
import com.ssm.service.ExamPaperService;
import com.ssm.service.ExamService;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 考试管理控制器
 */
@RequestMapping("/exam")
@Controller
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private UserService userService;

    /*转发考试管理页面*/
    @RequestMapping("/exam-list")
    public String examList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5",required = false)int pageSize,Model model) {
        List<Exam> examList=examService.getExamList(pageNum,pageSize,2);
        if (examList != null) {
            PageInfo<Exam> pageInfo = new PageInfo<>(examList, 3);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("count",examService.getExamCount());
        }
        model.addAttribute("examList",examList);
        return "exam-list";
    }

    /*转发添加考试页面*/
    @RequestMapping("/exam-add")
    public String examAdd(Model model) {
        model.addAttribute("examPaperList",examPaperService.getExamPaperAll());
        return "exam-add";
    }

    /**
     * 添加考试
     *
     * @param exam
     * @return
     */
    @PostMapping("/add-exam")
    public @ResponseBody
    Message addExam(@RequestBody Exam exam, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Message msg = new Message();
        try {
            exam.setCreator(user.getUserId());
            exam.setCreatorId(user.getUserName());
            exam.setApproved(1);
            examService.addExam(exam);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getClass().getName());
        }
        return msg;
    }

    /**
     * 模拟考试页面
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/model-test-list")
    public String modelTestList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "5", required = false)Integer pageSize,Model model) {
        List<Exam> examList=examService.getExamList(pageNum,pageSize,3);
        if (examList != null) {
            model.addAttribute("count", examService.getModelTestCount());
            PageInfo<Exam> pageInfo = new PageInfo<>(examList, 3);
            model.addAttribute("pageInfo", pageInfo);
        }
        model.addAttribute("examList",examList );
        return "model-test-list";
    }


    /**
     * 发布模拟考试页面
     * @param model
     * @return
     */
    @RequestMapping("/model-test-add")
    public String modelTestAdd(Model model){
        List<ExamPaper> examPaperList=examPaperService.getExamPaperAll();
        model.addAttribute("examPaperList",examPaperList );
        return "model-test-add";
    }

    /**
     * 添加模拟考试
     * @param exam
     * @return
     */
    @PostMapping("/add-model-test")
    public @ResponseBody Message addModelTest(@RequestBody Exam exam,HttpSession session) {
        User user = (User) session.getAttribute("user");
        Message msg = new Message();
        try {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, 10);
            exam.setCreator(user.getUserId());
            exam.setCreatorId(user.getUserName());
            exam.setApproved(1);
            exam.setEffTime(new Date());
            exam.setExpTime(c.getTime());
            examService.addExam(exam);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getClass().getName());
        }
        return msg;
    }

    /**
     * 考试学员名单
     *
     * @param model
     * @param request
     * @param examId
     * @return
     */
    @RequestMapping(value = "/exam-student-list/{examId}", method = RequestMethod.GET)
    private String examStudentListPage(Model model, HttpServletRequest request, @PathVariable int examId, @RequestParam(value="searchStr",required=false,defaultValue="") String searchStr, @RequestParam(value="order",required=false,defaultValue="") String order, @RequestParam(value="limit",required=false,defaultValue="0") int limit, @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
        User user = (User) request.getSession().getAttribute("user");
        if("".equals(searchStr))
            searchStr = null;
        if("".equals(order) || (!"desc".equals(order) && !"asc".equals(order)))
            order = null;
        List<ExamHistory> histList = examService.getUserExamHistListByExamId(examId, searchStr, order, limit);
        List<Group> groupList = userService.getGroupListByUserId(user.getUserId());
        model.addAttribute("groupList", groupList);
        model.addAttribute("histList", histList);
        model.addAttribute("examId", examId);
        model.addAttribute("limit", limit);
        model.addAttribute("order", order);
        model.addAttribute("searchStr", searchStr);
        model.addAttribute("count", examService.getUserExamHistListByExamIdCount(examId));
        return "user-exam-list";
    }

    /**
     * 改变用户考试申请的审核状态
     * @param histId
     * @param mark
     * @return
     */
    @RequestMapping(value = "/mark-hist/{histId}/{mark}", method = RequestMethod.GET)
    public @ResponseBody Message markUserExamHist(@PathVariable("histId") int histId,@PathVariable("mark") int mark){

        Message msg = new Message();
        try {
            examService.changeUserExamHistStatus(histId, mark);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getMessage());
        }
        return msg;
    }

    /**
     * 将用户添加到考试中
     * @param userNameStr
     * @param examId
     * @return
     */
    @RequestMapping(value = "/add-exam-user/{examId}", method = RequestMethod.POST)
    public @ResponseBody Message addExamUser(@RequestBody String userNameStr,@PathVariable("examId") int examId,HttpSession session) {
        /*User user = (User) session.getAttribute("user");
        userNameStr = userNameStr.replace("\"", "");
        Message msg = new Message();
        try {
            examService.addExamUser(examId, userNameStr, userInfo.getRoleMap());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getClass().getName());
        }*/
        return new Message();
    }

    /**
     * 删除考试
     * @param examId
     * @return
     */
    @RequestMapping(value = "/delete-exam/{examId}", method = RequestMethod.GET)
    public @ResponseBody Message deleteExam(@PathVariable("examId") int examId){

        Message msg = new Message();
        try {
            examService.deleteExamById(examId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getMessage());
        }
        return msg;
    }

    /**
     * 改变考试的审核状态
     * @param examId
     * @param mark
     * @return
     */
    @RequestMapping(value = "/mark-exam/{examId}/{mark}", method = RequestMethod.GET)
    public @ResponseBody Message markExam(@PathVariable("examId") int examId,@PathVariable("mark") int mark){

        Message msg = new Message();
        try {
            examService.changeExamStatus(examId, mark);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getMessage());
        }
        return msg;
    }

    /**
     * 学员试卷
     * @param model
     * @param request
     * @param histId
     * @return
     */
    @RequestMapping(value = "/student-answer-sheet/{histId}", method = RequestMethod.GET)
    private String studentAnswerSheetPage(Model model, HttpServletRequest request, @PathVariable int histId) {
        ExamHistory history = examService.getUserExamHistListByHistId(histId);
        int examPaperId = history.getExamPaperId();

        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
        StringBuilder sb = new StringBuilder();
        if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
            Gson gson = new Gson();
            String content = examPaper.getContent();
            List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>(){}.getType());

            for(QuestionQueryResult question : questionList){
                QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
                sb.append(adapter.getStringFromXML());
            }
        }

        model.addAttribute("htmlStr", sb);
        model.addAttribute("exampaperid", examPaperId);
        model.addAttribute("examHistoryId", history.getHistId());
        model.addAttribute("exampapername", examPaper.getName());
        model.addAttribute("examId", history.getExamId());
        return "student-answer-sheet";
    }

    /**
     * 人工阅卷
     * @param model
     * @param request
     * @param examhistoryId
     * @return
     */
    @RequestMapping(value = "/mark-exampaper/{examhistoryId}", method = RequestMethod.GET)
    private String markExampaperPage(Model model, HttpServletRequest request, @PathVariable int examhistoryId) {
        ExamHistory history = examService.getUserExamHistListByHistId(examhistoryId);
        int examPaperId = history.getExamPaperId();

        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
        StringBuilder sb = new StringBuilder();
        if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
            Gson gson = new Gson();
            String content = examPaper.getContent();
            List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>(){}.getType());

            for(QuestionQueryResult question : questionList){
                QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
                sb.append(adapter.getStringFromXML());
            }
        }

        model.addAttribute("htmlStr", sb);
        model.addAttribute("exampaperid", examPaperId);
        model.addAttribute("examHistoryId", history.getHistId());
        model.addAttribute("exampapername", examPaper.getName());
        model.addAttribute("examId", history.getExamId());
        return "exampaper-mark";
    }

    /**
     * 获取答题卡
     * @param histId
     * @return
     */
    @RequestMapping(value = "/get-answersheet/{histId}", method = RequestMethod.GET)
    public @ResponseBody AnswerSheet getAnswerSheet(@PathVariable("histId") int histId){
        ExamHistory history = examService.getUserExamHistListByHistId(histId);
        Gson gson = new Gson();
        AnswerSheet answerSheet = gson.fromJson(history.getAnswerSheet(), AnswerSheet.class);
        System.out.println("answerSheet = " + answerSheet);
        return answerSheet;
    }

    /**
     * 阅卷
     * @param answerSheet
     * @return
     */
    @RequestMapping(value = "/answersheet", method = RequestMethod.POST)
    public @ResponseBody Message submitAnswerSheet(@RequestBody AnswerSheet answerSheet){
        Gson gson = new Gson();
        float score = 0f;
        for(AnswerSheetItem item : answerSheet.getAnswerSheetItems()){
            score += item.getPoint();
            //TO-DO:模拟考试是否要记录主观题的历史？
        }
        answerSheet.setPointRaw(score);
        examService.updateUserExamHist(answerSheet, gson.toJson(answerSheet),3);
        return new Message();
    }

}
