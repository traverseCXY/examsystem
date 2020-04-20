package com.ssm.controller;

import com.ssm.entity.StandardPasswordEncoderForSha1;
import com.ssm.entity.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ExamPortalController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(){
        return "home";
    }


    @RequestMapping("/")
    public String redirectLogin(){
        return "redirect:/user/user-login";
    }

    @RequestMapping("/login")
    public String login(User user, HttpSession session) {
        String password = user.getPassword() + "{" + user.getUserName().toLowerCase() + "}";
        PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
        String resultPassword = passwordEncoder.encode(password);
        user.setPassword(resultPassword);
        User resultUser = userService.getUserByNameAndPassword(user);
        session.setAttribute("user",resultUser);
        if (resultUser != null) {
            return "redirect:/home";
        }
        return "redirect:/user/user-login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user",null);
        return "redirect:/user/user-login";
    }

}
