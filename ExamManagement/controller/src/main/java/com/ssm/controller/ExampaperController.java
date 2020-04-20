package com.ssm.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssm.entity.*;
import com.ssm.service.ExamPaperService;
import com.ssm.service.FieldService;
import com.ssm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/*试卷管理控制器*/
@Controller
@RequestMapping("/exampaper")
public class ExampaperController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private QuestionService questionService;

    /*转发到试卷管理页面*/
    @RequestMapping("/exampaper-list")
    public String exampaperList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,Model model) {
        model.addAttribute("fieldList",fieldService.getAll());
        model.addAttribute("paper",examPaperService.getExamPaperList(pageNum,pageSize));
        model.addAttribute("count", examPaperService.getExamPaperCount());
        return "exampaper-list";
    }


    @RequestMapping(value = "/exampaper-list/{paperType}", method = RequestMethod.GET)
    private String examPaperListPage(Model model, HttpServletRequest request, @PathVariable("paperType") Integer paperType, @RequestParam(value="searchStr",required=false,defaultValue="") String searchStr,  @RequestParam(value="pageSize",required=false,defaultValue="1") int pageNum){
        List<ExamPaper> paper = examPaperService.getExamPaperPageList(pageNum,10,paperType,searchStr);
        List<Field> fieldList = fieldService.getAll();
        model.addAttribute("fieldList", fieldList);
        model.addAttribute("paper", paper);
        model.addAttribute("searchStr", searchStr);
        return "exampaper-list";
    }

    /**
     * 修改试卷页面
     * @param model
     * @param request
     * @param exampaperId
     * @return
     */
    @RequestMapping(value = "/exampaper-edit/{exampaperId}")
    private String examPaperEditPage(Model model, HttpServletRequest request, @PathVariable int exampaperId){
        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";
        ExamPaper examPaper = examPaperService.getExamPaperById(exampaperId);
        StringBuilder sb = new StringBuilder();
        if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
            Gson gson = new Gson();
            List<QuestionQueryResult> questionList = gson.fromJson(examPaper.getContent(), new TypeToken<List<QuestionQueryResult>>(){}.getType());
            for(QuestionQueryResult question : questionList){
                QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
                sb.append(adapter.getStringFromXML());
            }
        }

        model.addAttribute("htmlStr", sb);
        model.addAttribute("exampaperid", exampaperId);
        model.addAttribute("exampapername", examPaper.getName());
        return "exampaper-edit";
    }

    /**
     * 批量添加试题
     * @param model
     * @param idList
     * @return
     */
    @RequestMapping(value = "/get-question-detail4add")
    public @ResponseBody
    List<QuestionQueryResult> getQuestion5add(Model model, HttpServletRequest request, @RequestBody List<Integer> idList) {
        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        Set<Integer> set = new TreeSet<Integer>();
        for(int id : idList){
            set.add(id);
        }
        idList.clear();
        Iterator<Integer> it = set.iterator();
        while(it.hasNext()){
            idList.add(it.next());
        }
        List<QuestionQueryResult> returnList = questionService.getQuestionDescribeListByIdList(idList);

        for(QuestionQueryResult question : returnList){
            QuestionAdapter adapter = new QuestionAdapter(question, strUrl);
            question.setContent(adapter.getStringFromXML());
        }
        return returnList;
    }

    @RequestMapping(value = "/update-exampaper/{examPaperId}", method = RequestMethod.POST)
    public @ResponseBody
    Message exampaperOnUpdate(Model model,
                              @PathVariable("examPaperId") int examPaperId,
                              @RequestBody HashMap<Integer, Float> questionPointMap) {
        System.out.println("examPaperId = " + examPaperId);
        System.out.println("questionPointMap = " + questionPointMap);
        Message message = new Message();
            ExamPaper examPaper = new ExamPaper();
            List<Integer> idList = new ArrayList<Integer>();
            Iterator<Integer> it = questionPointMap.keySet().iterator();
            float sum = 0;
            while(it.hasNext()){
                int key = it.next();
                idList.add(key);
            }
            List<QuestionQueryResult> questionList = questionService.getQuestionDescribeListByIdList(idList);
            AnswerSheet as = new AnswerSheet();
            as.setExamPaperId(examPaperId);
            List<AnswerSheetItem> asList = new ArrayList<AnswerSheetItem>();
            for(QuestionQueryResult q : questionList){
                AnswerSheetItem item = new AnswerSheetItem();
                item.setAnswer(q.getAnswer());
                item.setQuestionId(q.getQuestionId());
                item.setPoint(questionPointMap.get(q.getQuestionId()));
                item.setQuestionTypeId(q.getQuestionTypeId());
                q.setQuestionPoint(questionPointMap.get(q.getQuestionId()));
                sum += questionPointMap.get(q.getQuestionId());
                asList.add(item);
            }
            as.setPointMax(sum);
            as.setAnswerSheetItems(asList);
            Gson gson = new Gson();
            String content = gson.toJson(questionList);
            String answerSheet = gson.toJson(as);
            examPaper.setAnswerSheet(answerSheet);
            examPaper.setContent(content);
            examPaper.setTotalPoint(sum);
            examPaper.setId(examPaperId);

            //这两个属性区别试卷是否可用
            //examPaper.setIs_subjective(true);
            //examPaper.setStatus(1);
            examPaperService.updateExamPaper(examPaper);
        return message;
    }

    /**
     * 预览试卷
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/exampaper-preview/{examPaperId}")
    private String examPaperPreviewPage(Model model, HttpServletRequest request, @PathVariable int examPaperId){
        String strUrl = "http://" + request.getServerName() // 服务器地址
                + ":" + request.getServerPort() + "/";

        ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
        StringBuilder sb = new StringBuilder();
        if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
            Gson gson = new Gson();
            List<QuestionQueryResult> questionList = gson.fromJson(examPaper.getContent(), new TypeToken<List<QuestionQueryResult>>(){}.getType());

            for(QuestionQueryResult question : questionList){
                QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
                sb.append(adapter.getStringFromXML());
            }
        }

        model.addAttribute("htmlStr", sb);
        model.addAttribute("exampaperid", examPaperId);
        model.addAttribute("exampapername", examPaper.getName());
        return "exampaper-preview";
    }


    /**
     * 删除一张试卷
     * @param examPaperId
     * @return
     */
    @PostMapping("/paper-delete")
    public @ResponseBody Message deleteExamPaper(@RequestBody Integer examPaperId){
        Message message = new Message();
            ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
            if(examPaper.getStatus() == 1) {
                message.setResult("已发布的试卷不允许删除");
                return message;
            }
            examPaperService.deleteExamPaper(examPaperId);
        return message;
    }

    /**
     * 添加试卷页面
     * @param model
     * @return
     */
    @RequestMapping("/exampaper-add")
    public String exampaperAdd(Model model) {
        model.addAttribute("fieldList", fieldService.getAll());
        return "exampaper-add";
    }

    /**
     * 自动或者手动组卷(插入一张空试卷)
     *
     * @param param
     * @return
     */
    @PostMapping("/exampaper-add")
    public @ResponseBody Message createExamPaper(@RequestBody PaperCreatorParam param, HttpSession session) {
        System.out.println("param = " + param);
        User user = (User) session.getAttribute("user");
        Message message = new Message();
        ExamPaper examPaper = new ExamPaper();
        examPaper.setName(param.getPaperName());
        examPaper.setDuration(param.getTime());
        examPaper.setPassPoint(param.getPassPoint());
        examPaper.setFieldId(param.getPaperType());
        examPaper.setCreator(user.getUserName());
        examPaper.setTotalPoint(param.getPaperPoint());
        examPaper.setSubjective(true);
        //手工组卷
        if(param.getQuestionKnowledgePointRate().size() == 0){
            try{
                examPaperService.insertExamPaper(examPaper);
            }catch(Exception ex){
                message.setResult(ex.getMessage());
            }
            message.setGeneratedId(examPaper.getId());
            return message;
        }
        List<Integer> idList = new ArrayList<Integer>();
        HashMap<Integer, Float> knowledgeMap = param
                .getQuestionKnowledgePointRate();
        Iterator<Integer> it = knowledgeMap.keySet().iterator();
        while(it.hasNext()){
            idList.add(it.next());
        }
        HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap = questionService
                .getQuestionStrutsMap(idList);
        try{
            examPaperService.createExamPaper(questionMap, param.getQuestionTypeNum(),
                    param.getQuestionTypePoint(),
                    param.getQuestionKnowledgePointRate(), examPaper);
            message.setGeneratedId(examPaper.getId());
        }catch(Exception e){
            message.setResult(e.getMessage());
        }
        return message;
    }


}
