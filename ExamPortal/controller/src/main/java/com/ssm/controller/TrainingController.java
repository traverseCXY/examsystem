package com.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrainingController {


    /**
     * 培训资料页面
     * @return
     */
    @RequestMapping("/training-list")
    public String trainingList(){
        return "training-list";
    }
}
