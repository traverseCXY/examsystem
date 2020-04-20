package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.entity.*;
import com.ssm.service.FieldService;
import com.ssm.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 培训管理控制器
 */
@Controller
@RequestMapping("/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private FieldService fieldService;

    /**
     * 培训管理页面
     * @return
     */
    @RequestMapping("/training-list")
    public String trainingList(Model model, HttpServletRequest request, @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum){
        List<Training> trainingList = trainingService.getTrainingList(pageNum,10,-1);
        if (trainingList != null) {
            PageInfo<Training> pageInfo = new PageInfo<>(trainingList,3);
            model.addAttribute("pageInfo", pageInfo);
        }
        model.addAttribute("trainingList", trainingList);
        return "training-list";
    }

    /**
     * 添加培训页面
     * @return
     */
    @GetMapping("/training-add")
    public String trainingAdd(Model model){
        List<Field> fieldList = fieldService.getAll();
        model.addAttribute("fieldList", fieldList);
        return "training-add";
    }

    /**
     * 培训进度
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/student-training-history/{trainingId}", method = RequestMethod.GET)
    public String trainingHistoryPage(Model model, HttpServletRequest request, @PathVariable int trainingId,@RequestParam(value="searchStr",required=false,defaultValue="") String searchStr, @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
        List<UserTraining> userTrainingList = trainingService.getUserTrainingList(trainingId, -1,searchStr);
        List<Integer> idList = new ArrayList<Integer>();
        for(UserTraining userTraining : userTrainingList){
            idList.add(userTraining.getUserId());
        }
        Map<Integer,List<TrainingSectionProcess>> map = trainingService.getTrainingSectionProcessMapByTrainingId(trainingId, idList,searchStr);
        model.addAttribute("trainingId", trainingId);
        model.addAttribute("userTrainingList", userTrainingList);
        model.addAttribute("processMap", map);
        model.addAttribute("searchStr", searchStr);
        return "training-history";
    }

    /**
     * 培训章节
     * @param model
     * @param request
     * @param trainingId
     * @return
     */
    @RequestMapping(value = "/section-list/{trainingId}", method = RequestMethod.GET)
    public String sectionPage(Model model, HttpServletRequest request, @PathVariable int trainingId) {
        List<TrainingSection> sectionList = trainingService.getTrainingSectionByTrainingId(trainingId, -1);
        model.addAttribute("sectionList", sectionList);
        return "section";
    }

    /**
     * 添加一个培训
     * @param training
     * @return
     */
    @PostMapping("/add-training")
    public @ResponseBody
    Message addTraining(@RequestBody Training training, HttpSession session) {
        User user=(User)session.getAttribute("user");
        Message msg = new Message();
        try {
            training.setCreatorId(user.getUserId());
            trainingService.addTraining(training);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getClass().getName());
        }
        return msg;
    }
}
