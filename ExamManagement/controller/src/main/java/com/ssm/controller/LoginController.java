package com.ssm.controller;

import com.ssm.dao.UserMapper;
import com.ssm.entity.Field;
import com.ssm.entity.StandardPasswordEncoderForSha1;
import com.ssm.entity.User;
import com.ssm.service.FieldService;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class LoginController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String redirect(HttpSession session){
        if(session.getAttribute("user")!=null){
            return "redirect:/dashboard";
        }
        return "redirect:/user/user-login";
    }

    @RequestMapping("/user/user-login")
    public String login(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        return "login";
    }

    @RequestMapping("/user/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user",null);
        return "redirect:/user/user-login";
    }

    @RequestMapping("/user/user-register")
    public String register(){
        return "register";
    }



    @RequestMapping("/login")
    public String loging(HttpSession session, User user, HttpServletRequest request) throws UnsupportedEncodingException {
        String password = user.getPassword() + "{" + user.getUserName().toLowerCase() + "}";
        PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
        String resultPassword = passwordEncoder.encode(password);
        user.setPassword(resultPassword);
        User resultUser = userService.getUserByUserNameAndPassword(user);
        session.setAttribute("user",resultUser);
        if (resultUser != null && resultUser.getFieldId()==1) {
            return "redirect:/dashboard";
        }
        request.setAttribute("message", URLEncoder.encode("请检查账户或者密码是否输入正确！","utf-8"));
        return "redirect:/user/user-login";

    }


    @RequestMapping("/dashboard")
    public String adminDashboard(Model model){
        model.addAttribute("fieldList", fieldService.getAll());
        return "dashboard";
    }
}
