package com.logho.controller;

import com.logho.constants.UserContants;
import com.logho.exception.NotFountException;
import com.logho.pojo.User;
import com.logho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes loginMsg) {
        User user = userService.getUser(username, password);

        if (user!=null) {
            session.setAttribute(UserContants.LOGINED_USER,user);
            return "redirect:/";
        } else {
            loginMsg.addFlashAttribute("message","用户名或者密码输入错误！");
            System.out.println("===");
            return "redirect:/user/login";
        }

    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(UserContants.LOGINED_USER);
        return "redirect:/";
    }

    @PostMapping("/user/checkusername")
    @ResponseBody
    public String checkUsername(String username) {
        boolean flag = userService.checkUsername(username);
        if (flag) {
            return "success";
        }
        return "fail";
    }


    @PostMapping("/user/regist")
    public String registUser(User user, Model model) {
        boolean flag = userService.addUser(user);
        if (flag) {
            model.addAttribute("message","注册成功");
            return "redirect:/user/login";
        }else {
            throw new NotFountException();
        }
    }

}
