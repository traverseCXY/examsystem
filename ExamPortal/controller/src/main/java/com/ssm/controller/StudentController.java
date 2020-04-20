package com.ssm.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssm.entity.*;
import com.ssm.interceptor.UserInfo;
import com.ssm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionHistoryService questionHistoryService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamPaperService examPaperService;

    /*转发到试题练习页面*/
    @RequestMapping("/practice-list")
    public String precticeList(Model model, HttpSession session, @RequestParam(value="fieldId",required=false,defaultValue="0") int fieldId) {
        User user = (User) session.getAttribute("user");
        List<Field> fieldList = questionService.getAllField();
        if(fieldId == 0)
            fieldId = fieldList.get(0).getFieldId();
        Map<Integer, Map<Integer, QuestionStatistic>> questionMap = questionService.getTypeQuestionStaticByFieldId(fieldId);
        Map<Integer, Map<Integer, QuestionStatistic>> historyMap = questionHistoryService.getTypeQuestionHistStaticByFieldId(fieldId, user.getUserId());
        Map<Integer, QuestionStatistic> historyStatisticMap = questionHistoryService.getQuestionHistStaticByFieldId(fieldId, user.getUserId());
        Map<Integer, KnowledgePoint> pointMap = questionService.getKnowledgePointByFieldId(fieldId);

        List<KnowledgePointAnalysisResult> kparl = new ArrayList<KnowledgePointAnalysisResult>();
        for(Map.Entry<Integer, Map<Integer, QuestionStatistic>> entry : questionMap.entrySet()){
            KnowledgePointAnalysisResult kpar = new KnowledgePointAnalysisResult();
            kpar.setKnowledgePointId(entry.getKey());
            List<TypeAnalysis> tal = new ArrayList<TypeAnalysis>();
            int totalRightAmount = 0;
            int totalAmount = 0;
            for(Map.Entry<Integer, QuestionStatistic> entry1 : entry.getValue().entrySet()){
                TypeAnalysis ta = new TypeAnalysis();
                ta.setQuestionTypeId(entry1.getKey());
                ta.setQuestionTypeName(entry1.getValue().getQuestionTypeName());
                int rightAmount = 0;
                int wrongAmount = 0;
                try {
                    rightAmount = historyMap.get(entry.getKey()).get(entry1.getKey()).getRightAmount();
                } catch (Exception e) {}
                try {
                    wrongAmount = historyMap.get(entry.getKey()).get(entry1.getKey()).getWrongAmount();
                } catch (Exception e) {}
                ta.setRightAmount(rightAmount);
                ta.setWrongAmount(wrongAmount);
                ta.setRestAmount(entry1.getValue().getAmount() - rightAmount - wrongAmount);
                tal.add(ta);
                if(kpar.getKnowledgePointName() == null)
                    kpar.setKnowledgePointName(entry1.getValue().getPointName());
                totalRightAmount += rightAmount;
                totalAmount += entry1.getValue().getAmount();
            }
            kpar.setTypeAnalysis(tal);
            if(totalAmount > 0)
                kpar.setFinishRate((float)totalRightAmount / (float)totalAmount);
            kparl.add(kpar);
        }
        model.addAttribute("kparl", kparl);
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("historyMap", historyStatisticMap);
        model.addAttribute("pointMap", pointMap);
        model.addAttribute("fieldList", fieldList);
        return "practice";
    }

    /**
     * 强化练习
     * @param model
     * @param request
     * @param knowledgePointId
     * @param questionTypeId
     * @return
     */
    @RequestMapping(value = "/practice-improve/{fieldId}/{knowledgePointId}/{questionTypeId}", method = RequestMethod.GET)
    public String practiceImprove(Model model, HttpServletRequest request,
                                  @PathVariable("fieldId") int fieldId,
                                  @PathVariable("knowledgePointId") int knowledgePointId,
                                  @PathVariable("questionTypeId") int questionTypeId) {

        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        List<QuestionQueryResult> qqrList = questionService
                .getQuestionAnalysisListByPointIdAndTypeId(questionTypeId,
                        knowledgePointId);
        String questionTypeName = "";
        String fieldName = "";
        try{
            fieldName = qqrList.get(0).getPointName().split(">")[1];
        }catch(Exception e){
            //log.info(e.getMessage());
        }

        Map<Integer,QuestionType> questionTypeMap = questionService.getQuestionTypeMap();
        for(Map.Entry<Integer,QuestionType> entry : questionTypeMap.entrySet()){

            if(entry.getKey() == questionTypeId){
                questionTypeName = entry.getValue().getName();
                break;
            }
        }
        int amount = qqrList.size();
        StringBuilder sb = new StringBuilder();
        for(QuestionQueryResult qqr : qqrList){
            QuestionAdapter adapter = new QuestionAdapter(qqr,strUrl);
            sb.append(adapter.getStringFromXML());
        }

        model.addAttribute("questionStr", sb.toString());
        model.addAttribute("amount", amount);
        model.addAttribute("fieldName", fieldName);
        model.addAttribute("questionTypeName", questionTypeName);
        model.addAttribute("practiceName", "强化练习");
        model.addAttribute("knowledgePointId", knowledgePointId);
        model.addAttribute("questionTypeId", questionTypeId);
        model.addAttribute("fieldId", fieldId);
        return "practice-improve-qh";
    }


    /**
     * 获取用户的练习记录（试题ID）
     * @param model
     * @param request
     * @param fieldId
     * @param knowledgePointId
     * @param questionTypeId
     * @return
     */
    @RequestMapping(value = "/practice-improve-his/{fieldId}/{knowledgePointId}/{questionTypeId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Integer> getFinishedQuestionId(Model model, HttpServletRequest request, @PathVariable("fieldId") int fieldId,
                                        @PathVariable("knowledgePointId") int knowledgePointId, @PathVariable("questionTypeId") int questionTypeId){
        User user = (User) request.getSession().getAttribute("user");
        Map<Integer,List<UserQuestionHistory>> historyMap = questionHistoryService.getUserQuestionHist(user.getUserId(), fieldId);
        List<Integer> l = new ArrayList<Integer>();
        return l;

    }


    /**
     * 随机练习页面
     * @param model
     * @param request
     * @param fieldId
     * @return
     */
    @RequestMapping(value = "/practice-test/{fieldId}", method = RequestMethod.GET)
    public String practiceStartNew(Model model, HttpServletRequest request,@PathVariable("fieldId") int fieldId) {

        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        Map<Integer, Map<Integer, List<QuestionQueryResult>>>  map = questionService.getQuestionMapByFieldId(fieldId);
        List<QuestionQueryResult> qqrList = new ArrayList<QuestionQueryResult>();
        for(Map.Entry<Integer, Map<Integer, List<QuestionQueryResult>>> entry : map.entrySet()){
            if(entry.getValue().containsKey(1))
                qqrList.addAll(entry.getValue().get(1));
            if(entry.getValue().containsKey(2))
                qqrList.addAll(entry.getValue().get(2));
            if(entry.getValue().containsKey(3))
                qqrList.addAll(entry.getValue().get(3));
        }
        int amount = 0;
        if(qqrList.size() == 0){
            model.addAttribute("errorMsg", "无可用的题目");
            return "error";
        }

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0 ; i < 20 ; i ++){
            int questionCount = qqrList.size();
            int index = random.nextInt(questionCount);
            QuestionAdapter adapter = new QuestionAdapter(qqrList.get(index),strUrl);
            sb.append(adapter.getStringFromXML());
            qqrList.remove(index);
            amount ++;
            if(qqrList.size() == 0)
                break;
        }
        model.addAttribute("questionStr", sb.toString());
        model.addAttribute("amount", amount);
        model.addAttribute("questionTypeName", "随机题");
        model.addAttribute("practiceName", "随机练习");
        return "practice-improve";
    }

    /**
     * 个人设置页面
     * @return
     */
    @RequestMapping("/setting")
    public String setting(){
        return "setting";
    }

    /**
     * 修改密码页面
     * @return
     */
    @GetMapping("/change-password" )
    public String changePasswordPage() {
        return "change-password";
    }

    /**
     * 修改密码
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/change-pwd")
    public @ResponseBody Message changePassword(@RequestBody User user,HttpSession session){
        Message message = new Message();
        User myuser = (User) session.getAttribute("user");
        try{
            String password = user.getPassword() + "{" + myuser.getUserName() + "}";
            PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
            String resultPassword = passwordEncoder.encode(password);
            user.setPassword(resultPassword);
            user.setUserName(myuser.getUserName());
            System.out.println("user = " + user);
            userService.updateUserPwd(user, null);
        }catch(Exception e){
            e.printStackTrace();
            message.setResult(e.getClass().getName());
        }
        return message;
    }

    /**
     * 错题练习
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/practice-incorrect/{fieldId}/{knowledgePointId}", method = RequestMethod.GET)
    public String practiceIncorrectQuestions(Model model, HttpServletRequest request,@PathVariable("fieldId") int fieldId,@PathVariable("knowledgePointId") int knowledgePointId) {
        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";
        User user = (User) request.getSession().getAttribute("user");

        Map<Integer,List<UserQuestionHistory>> historyMap = questionHistoryService.getUserQuestionHist(user.getUserId(), fieldId);

        List<UserQuestionHistory> historyList = historyMap.get(knowledgePointId);

        List<Integer> idList = new ArrayList<Integer>();
        for(UserQuestionHistory history : historyList){
            if(!history.isRight())
                idList.add(history.getQuestionId());
        }

        List<QuestionQueryResult> qqrList = new ArrayList<QuestionQueryResult>();
        if(idList.size() > 0)
            qqrList = questionService.getQuestionAnalysisListByIdList(idList);

        int amount = idList.size();
        StringBuilder sb = new StringBuilder();
        for(QuestionQueryResult qqr : qqrList){
            QuestionAdapter adapter = new QuestionAdapter(qqr,strUrl);
            sb.append(adapter.getStringFromXML());
        }

        model.addAttribute("questionStr", sb.toString());
        model.addAttribute("amount", amount);
        model.addAttribute("questionTypeName", "错题库");
        model.addAttribute("practiceName", "错题练习");
        return "practice-improve";
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = { "/update-user" }, method = RequestMethod.POST)
    public @ResponseBody Message updateUser(@RequestBody User user,HttpSession session) {
        System.out.println("user = " + user);
        User upUser = (User) session.getAttribute("user");
        String password = user.getPassword() + "{" + user.getUserName() + "}";
        PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
        String resultPassword = "";
        if(user.getPassword() != null)
            resultPassword = "".equals(user.getPassword().trim()) ? "" : passwordEncoder.encode(password);
        user.setPassword(resultPassword);
        user.setEnabled(true);
        Message message = new Message();
        try {
            userService.updateUser(user, null);
            upUser.setTrueName(user.getTrueName());
            upUser.setEmail(user.getEmail());
            upUser.setNationalId(user.getNationalId());
            upUser.setPhoneNum(user.getPhoneNum());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if(e.getMessage().contains(user.getUserName())){
                message.setResult("duplicate-username");
                message.setMessageInfo("重复的用户名");
            } else if(e.getMessage().contains(user.getNationalId())){
                message.setResult("duplicate-national-id");
                message.setMessageInfo("重复的身份证");
            } else if(e.getMessage().contains(user.getEmail())){
                message.setResult("duplicate-email");
                message.setMessageInfo("重复的邮箱");
            } else if(e.getMessage().contains(user.getPhoneNum())){
                message.setResult("duplicate-phone");
                message.setMessageInfo("重复的电话");
            } else{
                message.setResult(e.getCause().getMessage());
                e.printStackTrace();
            }
        }
        return message;
    }

    /**
     * 学员试卷
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(  "/student-answer-sheet/{examId}")
    private String studentAnswerSheetPage(Model model, HttpServletRequest request, @PathVariable int examId) {
        User user = (User) request.getSession().getAttribute("user");
        ExamHistory history = examService.getUserExamHistByUserIdAndExamId(user.getUserId(), examId, 2, 3);
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
     * 获取答题卡
     * @param histId
     * @return
     */
    @RequestMapping("/get-answersheet/{histId}")
    public @ResponseBody AnswerSheet getAnswerSheet(@PathVariable("histId") int histId){
        ExamHistory history = examService.getUserExamHistListByHistId(histId);
        Gson gson = new Gson();
        AnswerSheet answerSheet = gson.fromJson(history.getAnswerSheet(), AnswerSheet.class);
        return answerSheet;
    }

    /**
     * 分析报告页面
     * @param model
     * @param examId
     * @return
     */
    @RequestMapping(value = "/finish-exam/{examId}", method = RequestMethod.GET)
    public String examFinishedPage(Model model,@PathVariable("examId") int examId,HttpSession session) {
        User user = (User) session.getAttribute("user");
        ExamHistory history = examService.getUserExamHistByUserIdAndExamId(user.getUserId(), examId, 2, 3);
        Gson gson = new Gson();
        List<QuestionQueryResult> questionList = gson.fromJson(history.getContent(), new TypeToken<List<QuestionQueryResult>>(){}.getType());

        List<Integer> idList = new ArrayList<Integer>();
        for (QuestionQueryResult q : questionList) {
            idList.add(q.getQuestionId());
        }

        AnswerSheet as = gson.fromJson(history.getAnswerSheet(), AnswerSheet.class);

        HashMap<Integer, AnswerSheetItem> hm = new HashMap<Integer,AnswerSheetItem>();
        for(AnswerSheetItem item : as.getAnswerSheetItems()){
            hm.put(item.getQuestionId(), item);
        }

        int total = questionList.size();
        int wrong = 0;
        int right = 0;

        HashMap<Integer, QuestionStatistic> reportResultMap = new HashMap<Integer, QuestionStatistic>();
        List<QuestionQueryResult> questionQueryList = questionService.getQuestionAnalysisListByIdList(idList);
        Map<Integer,KnowledgePoint> pointMap = questionService.getKnowledgePointByFieldId(null);
        HashMap<Integer, Boolean> answer = new HashMap<Integer, Boolean>();

        for(QuestionQueryResult result : questionQueryList){
            QuestionStatistic statistic = reportResultMap.get(result.getKnowledgePointId());
            if(statistic == null)
                statistic = new QuestionStatistic();
            statistic.setPointId(result.getKnowledgePointId());
            statistic.setPointName(pointMap.get(result.getKnowledgePointId()).getPointName());
            statistic.setAmount(statistic.getAmount() + 1);
            if(hm.get(result.getQuestionId()).isRight()){
                statistic.setRightAmount(statistic.getRightAmount() + 1);
                right ++;
                answer.put(result.getQuestionId(), true);
            }else{
                statistic.setWrongAmount(statistic.getWrongAmount() + 1);
                wrong ++;
                answer.put(result.getQuestionId(), false);
            }
            total ++;
            reportResultMap.put(result.getKnowledgePointId(), statistic);
        }

        model.addAttribute("total", total);
        model.addAttribute("wrong", wrong);
        model.addAttribute("right", right);
        model.addAttribute("reportResultMap", reportResultMap);
        model.addAttribute("create_time", history.getCreateTime());
        model.addAttribute("answer", answer);
        model.addAttribute("idList", idList);
        return "exam-finished";
    }


}
