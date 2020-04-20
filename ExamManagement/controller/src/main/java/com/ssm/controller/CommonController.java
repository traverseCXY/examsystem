package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.entity.*;
import com.ssm.service.FieldService;
import com.ssm.service.KnowledgePointService;
import com.ssm.service.QuestionService;
import com.ssm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用数据管理控制器
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private TagService tagService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private KnowledgePointService knowledgePointService;

    @Autowired
    private QuestionService questionService;

    /*转发到通用数据标签管理页面*/
    @RequestMapping("/tag-list")
    public String commonTagList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize, Model model) {
        List<Tag> tagList=tagService.getTags(pageNum, pageSize);
        PageInfo<Tag> pageInfo=new PageInfo<>(tagList,3);
        model.addAttribute("tagList",tagList );
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("count", questionService.getTagCount());
        return "tag-list";
    }

    /**
     * 删除一个Tag
     * @param id
     * @return
     */
    @RequestMapping("/tag-delete")
    @ResponseBody
    public Message tagDelete(Integer id) {
        tagService.deleteTagById(id);
        return new Message();
    }

    /**
     * 添加一个Tag
     * @param tag
     * @param session
     * @return
     */
    @RequestMapping("/tag-add")
    @ResponseBody
    public Message addTag(@RequestBody Tag tag, HttpSession session) {
        System.out.println("tag = " + tag);
        User user = (User) session.getAttribute("user");
        tag.setCreator(user.getUserId());
        tagService.addTag(tag);
        return new Message();
    }


    /**
     * 专业题库管理
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/field-list")
    public String commonFieldList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize, Model model) {
        List<Field> fieldList=fieldService.getFields(pageNum, pageSize);
        PageInfo<Field> pageInfo = new PageInfo<>(fieldList,3);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("fieldList", fieldList);
        model.addAttribute("count", fieldService.getFieldCount());
        return "field-list";
    }

    /**
     * 添加一个专业题库
     * @param field
     * @return
     */
    @RequestMapping("/field-add")
    @ResponseBody
    public Message addFile(@RequestBody Field field){
        fieldService.addField(field);
        return new Message();
    }

    /**
     * 知识分类管理页面
     * @param pageNum
     * @param pageSize
     * @param model
     * @param fieldId
     * @return
     */
    @RequestMapping("/knowledge-list")
    public String commonKnowledgeList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize, Model model,
                                      @RequestParam(value = "fieldId", defaultValue = "0", required = false) Integer fieldId) {
        List<KnowledgePoint> pointList;
        if (fieldId > 0) {
            pointList= knowledgePointService.getKnowledgePointsByFieldId(fieldId);
        } else {
            pointList= knowledgePointService.getKnowledgePoints(pageNum, pageSize);
        }
        model.addAttribute("pointList", pointList);
        model.addAttribute("fieldList", fieldService.getAll());
        PageInfo<KnowledgePoint> pageInfo = new PageInfo<>(pointList, 3);
        model.addAttribute("pageInfo",pageInfo);
        return "knowledge-list";
    }


    /**对知识分类筛选之后分页
     * @param model
     * @param fieldId
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/knowledge-list/{fieldId}", method = RequestMethod.GET)
    public String knowledgeListPage(Model model,@PathVariable("fieldId") int fieldId, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum){
        List<Field> fieldList = fieldService.getAll();
        List<KnowledgePoint> pointList = questionService.getKnowledgePointListByFieldId(pageNum,10,fieldId);
        model.addAttribute("pointList", pointList);
        model.addAttribute("fieldList", fieldList);
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("count", questionService.getKnowledgePoinCount());
        PageInfo<KnowledgePoint> pageInfo = new PageInfo<>(pointList, 3);
        model.addAttribute("pageInfo",pageInfo);
        return "knowledge-list";
    }

    @RequestMapping("/knowledge-PointPageList")
    @ResponseBody
    public PageInfo PointPageList(Model model,@RequestParam(value = "fieldId",defaultValue = "0",required = false) int fieldId, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum){
        List<Field> fieldList = fieldService.getAll();
        List<KnowledgePoint> pointList = questionService.getKnowledgePointListByFieldId(pageNum,10,fieldId);
        model.addAttribute("pointList", pointList);
        model.addAttribute("fieldList", fieldList);
        model.addAttribute("fieldId", fieldId);
        PageInfo<KnowledgePoint> pageInfo = new PageInfo<>(pointList, 3);
        model.addAttribute("pageInfo",pageInfo);
        return pageInfo;
    }

    /**
     * 添加一个知识分类
     * @param knowledgePoint
     * @return
     */

    @PostMapping("/point-add")
    public @ResponseBody Message addKnowledge(@RequestParam KnowledgePoint knowledgePoint){
        System.out.println("knowledgePoint = " + knowledgePoint);
        //knowledgePointService.addKnowledge(knowledgePoint);
        return new Message();
    }

    /**
     * 删除一个专业
     * @param model
     * @param fieldId
     * @return
     */
    @RequestMapping(value = "/delete-field-{fieldId}", method = RequestMethod.GET)
    public @ResponseBody
    Message deleteField(Model model, @PathVariable("fieldId") int fieldId){
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(fieldId);
        Message msg = new Message();
        try {
            questionService.deleteFieldByIdList(idList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getClass().getName());
        }
        return msg;

    }

    /**
     * 删除一个知识分类
     * @param model
     * @param pointId
     * @return
     */
    @RequestMapping("/delete-point-{pointId}")
    public @ResponseBody Message deleteKnowledgePoint(Model model,@PathVariable("pointId") int pointId){
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(pointId);
        Message msg = new Message();
        try {
            questionService.deleteKnowledgePointByIdList(idList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getClass().getName());
        }
        return msg;
    }


}
