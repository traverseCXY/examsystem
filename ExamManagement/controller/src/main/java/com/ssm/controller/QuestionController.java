package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ssm.entity.*;
import com.ssm.service.FieldService;
import com.ssm.service.KnowledgePointService;
import com.ssm.service.QuestionService;
import com.ssm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 试题管理控制器
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagService tagService;

    @Autowired
    private KnowledgePointService knowledgePointService;

    @Autowired
    private FieldService fieldService;


    /**
     * 试题管理页面
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/question-list")
    public String questionList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize, Model model) {
        QuestionFilter filter=new QuestionFilter();
        filter.setFieldId(0);
        filter.setKnowledge(0);
        filter.setQuestionType(0);
        filter.setSearchParam("-1");
        List<Question> questionList = questionService.getQuestions(pageNum, pageSize,filter);
        PageInfo<Question> pageInfo=new PageInfo<>(questionList,3);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("questionList", questionList);
        model.addAttribute("fieldList", fieldService.getAll());
        model.addAttribute("knowledgeList", knowledgePointService.getAll());
        model.addAttribute("questionTypeList", questionService.getQuestionTypesAll());
        model.addAttribute("tagList", tagService.getAll());
        model.addAttribute("count", questionService.getQuestionCount());
        return "question-list";
    }

    /**
     * 添加考试页面
     * @param model
     * @return
     */
    @RequestMapping("/question-add")
    public String questionAdd(Model model) {
        model.addAttribute("fieldList",fieldService.getAll());
        return "question-add";
    }

    /**
     * 导入试题页面-功能未实现
     * @return
     */
    @RequestMapping("/question-import")
    public String questionImport() {
        return "question-import";
    }

    @RequestMapping("/question-preview/{questionId}")
    public String questionPreviewPage(Model model, @PathVariable("questionId") int questionId, HttpServletRequest request) {
        String strUrl = "http://" + request.getServerName() //服务器地址
                + ":"
                + request.getServerPort() + "/";

        Question question = questionService.getQuestionById(questionId);
        List<Integer> idList = new ArrayList();
        idList.add(questionId);
        List<QuestionQueryResult> questionQueryList = questionService.getQuestionDescribeListByIdList(idList);
        HashMap<Integer, QuestionQueryResult> questionMap = new HashMap<Integer, QuestionQueryResult>();
        for (QuestionQueryResult qqr : questionQueryList) {
            if (questionMap.containsKey(qqr.getQuestionId())) {
                QuestionQueryResult a = questionMap.get(qqr.getQuestionId());
                questionMap.put(qqr.getQuestionId(), a);
            } else {
                questionMap.put(qqr.getQuestionId(), qqr);
            }
        }
        QuestionAdapter adapter = new QuestionAdapter(question, null, questionMap.get(questionId), strUrl);
        String strHtml = adapter.getStringFromXML(true, false, true);
        model.addAttribute("strHtml", strHtml);
        model.addAttribute("question", question);
        return "question-preview";
    }

    @RequestMapping("/question-detail/{questionId}")
    @ResponseBody
    public Message questionDetail(@PathVariable("questionId") int questionId) {
        Message message = new Message();
        //UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Question question = questionService.getQuestionDetail(questionId);
            message.setObject(question);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            message.setResult(e.getCause().getMessage());
        }
        return message;
    }

    @RequestMapping("/get-knowledge-point/{fieldId}")
    public @ResponseBody Message getQuestionPointByFieldId(@PathVariable int fieldId) {
        Message message = new Message();
        HashMap<Integer, String> pointMap = new HashMap<Integer, String>();
        List<KnowledgePoint> pointList = questionService.getKnowledgePointByFieldId(fieldId);
        for (KnowledgePoint point : pointList) {
            pointMap.put(point.getPointId(), point.getPointName());
        }
        System.out.println("pointList = " + pointList);
        message.setObject(pointMap);
        return message;
    }

    /**
     * 修改试题
     * @param jsonStr
     * @return
     */
    @RequestMapping("/question-update")
    public @ResponseBody Message updateQuestion(@RequestBody String jsonStr){
        Message msg = new Message();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonStr);
        List<QuestionTag> questionTagList = gson.fromJson(element.getAsJsonObject().get("tags"), new TypeToken<ArrayList<QuestionTag>>(){}.getType());
        Question question = gson.fromJson(element.getAsJsonObject().get("question"), Question.class);
        try {
            questionService.updateQuestion(question, questionTagList);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getCause().getMessage());
        }
        //TO-DO:需要提交到数据库，保证在事务中提交
        return msg;
    }

    /**
     * 根据试题id删除试题
     * @param model
     * @param questionId
     * @return
     */
    @RequestMapping("/delete-question/{questionId}")
    public @ResponseBody Message deleteQuestion(Model model, @PathVariable("questionId") int questionId) {
        Message message = new Message();
        try {
            questionService.deleteQuestionByID(questionId);
        } catch (Exception ex) {
            message.setResult(ex.getClass().getName());
        }
        return message;
    }

    /**
     * 添加试题
     *
     * @param question
     * @return
     */
    @RequestMapping("/question-insert")
    public @ResponseBody Message insertQuestion(@RequestBody Question question, HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        Message message = new Message();
        Gson gson = new Gson();
        question.setContent(gson.toJson(question.getQuestionContent()));
        question.setCreate_time(new Date());
        question.setCreator(user.getUserName());
        try {
            questionService.addQuestion(question);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message.setResult("error");
            message.setMessageInfo(e.getClass().getName());
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 题库页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/question-list/filterdialog-{fieldId}-{knowledge}-{questionType}-{searchParam}-{page}.html")
    public String questionListFilterDialogPage(Model model,
                                               @PathVariable("fieldId") int fieldId,
                                               @PathVariable("knowledge") int knowledge,
                                               @PathVariable("questionType") int questionType,
                                               @PathVariable("searchParam") String searchParam,
                                               @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
                                               ) {

        QuestionFilter qf = new QuestionFilter();
        qf.setFieldId(fieldId);
        qf.setKnowledge(knowledge);
        qf.setQuestionType(questionType);
        if (searchParam.equals("0"))
            searchParam = "-1";
        qf.setSearchParam(searchParam);

        List<Question> questionList = questionService.getQuestions(pageNum,pageSize,qf);

        model.addAttribute("fieldList", fieldService.getAll());

        model.addAttribute("knowledgeList",
                questionService.getKnowledgePointByFieldId(fieldId));

        model.addAttribute("questionTypeList",
                questionService.getQuestionTypesAll());

        model.addAttribute("questionFilter", qf);
        model.addAttribute("questionList", questionList);

        //保存筛选信息，删除后跳转页面时使用
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("knowledge", knowledge);
        model.addAttribute("questionType", questionType);
        model.addAttribute("searchParam", searchParam);
        model.addAttribute("tagList", tagService.getAll());
        PageInfo pageInfo=new PageInfo(questionList,3);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("count",questionService.getQuestionCount());
        return "question-list-dialog";
    }

    /**
     * 题库页面分页
     * @param model
     * @param questionFilter
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/question-list/filterdialogPage")
    @ResponseBody
    public PageInfo filterdialogPage(Model model,@RequestBody QuestionFilter questionFilter,
                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ) {
        List<Question> questionList = questionService.getQuestions(questionFilter.getPageNum(),pageSize,questionFilter);
        PageInfo pageInfo=new PageInfo(questionList,3);
        model.addAttribute("count",questionService.getQuestionCount());
        model.addAttribute("pageInfo",pageInfo);
        return pageInfo;
    }

    @RequestMapping(value = "/question-list/filter-{fieldId}-{knowledge}-{questionType}-{tag}-{searchParam}-{pageNum}.html")
    public String questionListFilterPage(Model model,
                                         @PathVariable("fieldId") Integer fieldId,
                                         @PathVariable("knowledge") Integer knowledge,
                                         @PathVariable("questionType") Integer questionType,
                                         @PathVariable("tag") Integer tag,
                                         @PathVariable("searchParam") String searchParam,
                                         @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        QuestionFilter qf = new QuestionFilter();
        qf.setFieldId(fieldId);
        qf.setKnowledge(knowledge);
        qf.setQuestionType(questionType);
        qf.setTag(tag);
        if (searchParam.equals("0"))
            searchParam = "-1";
        qf.setSearchParam(searchParam);
            //TODO 查找questionlist的时候需要tag
        List<Question> questionList = questionService.getQuestions(pageNum,pageSize,qf);
        PageInfo<Question> pageInfo=new PageInfo<>(questionList,3);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("count", questionService.getQuestionCount());
        List<Field> fieldList = fieldService.getAll();
        model.addAttribute("fieldList", fieldList);
        model.addAttribute("knowledgeList",
                questionService.getKnowledgePointByFieldId(fieldId));

        model.addAttribute("questionTypeList",
                questionService.getQuestionTypesAll());

        model.addAttribute("questionFilter", qf);
        model.addAttribute("questionList", questionList);
        model.addAttribute("tagList", tagService.getAll());
        //保存筛选信息，删除后跳转页面时使用
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("knowledge", knowledge);
        model.addAttribute("questionType", questionType);
        model.addAttribute("searchParam", "-1".equals(searchParam)?"":searchParam);
        return "question-list";
    }

    @RequestMapping(value = "/question-list/filter")
    @ResponseBody
    public PageInfo filterPage(Model model,@RequestBody QuestionFilter questionFilter,
                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ) {
        List<Question> questionList = questionService.getQuestions(questionFilter.getPageNum(),pageSize,questionFilter);
        PageInfo pageInfo=new PageInfo(questionList,3);
        model.addAttribute("pageInfo",pageInfo);
        return pageInfo;
    }


}
