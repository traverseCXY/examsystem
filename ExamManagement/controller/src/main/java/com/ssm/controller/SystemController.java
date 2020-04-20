package com.ssm.controller;

import com.ssm.entity.Message;
import com.ssm.entity.News;
import com.ssm.entity.StandardPasswordEncoderForSha1;
import com.ssm.entity.User;
import com.ssm.service.NewService;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 系统设置控制器
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private NewService newService;

    @Autowired
    private UserService userService;

    /**
     * 系统公告页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/news-list", method = RequestMethod.GET)
    private String newsListPage(Model model, HttpServletRequest request, @RequestParam(value="page",required=false,defaultValue="1") int page) {
        List<News> newsList = newService.getNewsList(page,1);
        model.addAttribute("newsList", newsList);
        return "news-list";
    }

    /**
     * 发布公告
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/news-add", method = RequestMethod.GET)
    private String newsAddPage(Model model, HttpServletRequest request) {
        return "";
    }

    /**
     * 后台管理员列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin-list", method = RequestMethod.GET)
    private String adminListPage(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<User> userList = userService.getUserListByRoleId(user.getUserId());
        model.addAttribute("userList", userList);
        return "sys-admin-list";
    }

    /**
     * 添加后台管理员
     * @param user
     * @return
     */
    @RequestMapping(value = { "/add-admin" }, method = RequestMethod.POST)
    public @ResponseBody Message addUser(@RequestBody User user) {
        System.out.println("user = " + user);
        Message message = new Message();
        user.setCreateTime(new Date());
        String password = user.getPassword() + "{" + user.getUserName().toLowerCase() + "}";
        PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
        String resultPassword = passwordEncoder.encode(password);
        user.setPassword(resultPassword);
        user.setEnabled(true);
        user.setUserName(user.getUserName().toLowerCase());
        try {
            userService.addUser(user);
        } catch (Exception e) {
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
     * 修改信息
     * @param user
     * @return
     */
    @RequestMapping(value = { "/update-user" }, method = RequestMethod.POST)
    public @ResponseBody Message updateUser(@RequestBody User user, HttpSession session) {
        User user1 = (User) session.getAttribute("user");
        user.setCreateTime(new Date());
        String password = user.getPassword() + "{" + user.getUserName() + "}";
        PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
        String resultPassword = "";
        if(user.getPassword() != null)
            resultPassword = "".equals(user.getPassword().trim()) ? "" : passwordEncoder.encode(password);
        user.setPassword(resultPassword);
        user.setEnabled(true);
        Message message = new Message();
        try {
            userService.updateUser(user);
        } catch (Exception e) {
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
     * 修改密码
     * @param model
     * @param password
     * @param userName
     * @return
     */
    @RequestMapping(value = "/reset-pwd-user/{password}/{userName}", method = RequestMethod.GET)
    public @ResponseBody Message resetUserPwd(Model model,@PathVariable("password") String password,@PathVariable("userName") String userName){
        Message msg = new Message();
        try {
            userService.updateUserPwd(userName, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg.setResult(e.getMessage());
        }
        return msg;
    }

}
