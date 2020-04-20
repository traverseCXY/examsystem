package com.ssm.controller;

import com.ssm.entity.Department;
import com.ssm.entity.Message;
import com.ssm.entity.StandardPasswordEncoderForSha1;
import com.ssm.entity.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*转发到登录页面*/
    @RequestMapping("/user-login")
    public String userLogin() {
        return "login";
    }

    /*转发到添加用户页面*/
    @RequestMapping("/user-register")
    public String userRegister(Model model) {
        List<Department> depList = userService.getDepList();
        model.addAttribute("depList", depList);
        return "register";
    }


    /**
     * 添加用户
     *
     * @param user
     * @param groupId 如果添加的用户为学员，必须指定groupId。如果添加的用户为教师，则groupId为任意数字
     * @return
     */
    @RequestMapping(value = {"/add-user"}, method = RequestMethod.POST)
    public @ResponseBody
    Message addUser(@RequestBody User user) {
        user.setCreateTime(new Date());

        String password = user.getPassword() + "{" + user.getUserName().toLowerCase() + "}";
        PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
        String resultPassword = passwordEncoder.encode(password);
        user.setPassword(resultPassword);
        user.setEnabled(true);
        user.setCreateBy(-1);
        user.setUserName(user.getUserName().toLowerCase());
        Message message = new Message();
        try {
            userService.addUser(user, "ROLE_STUDENT", 0, userService.getRoleMap());
        } catch (Exception e) {
            // TODO Auto-generated catch block

            if (e.getMessage().contains(user.getUserName())) {
                message.setResult("duplicate-username");
                message.setMessageInfo("重复的用户名");
            } else if (e.getMessage().contains(user.getNationalId())) {
                message.setResult("duplicate-national-id");
                message.setMessageInfo("重复的身份证");
            } else if (e.getMessage().contains(user.getEmail())) {
                message.setResult("duplicate-email");
                message.setMessageInfo("重复的邮箱");
            } else if (e.getMessage().contains(user.getPhoneNum())) {
                message.setResult("duplicate-phone");
                message.setMessageInfo("重复的电话");
            } else {
                message.setResult(e.getCause().getMessage());
                e.printStackTrace();
            }
        }
        return message;
    }


}
