package com.logho.controller.admin;

import com.logho.constants.UserContants;
import com.logho.pojo.User;
import com.logho.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes loginMsg) {
        User user = adminUserService.getUser(username, password);

        if (user!=null) {
            session.setAttribute(UserContants.LOGINED_USER,user);
            return "redirect:/admin/index";
        } else {
            loginMsg.addFlashAttribute("message","用户名或者密码输入错误！");
            System.out.println("===");
            return "redirect:/admin";
        }

    }
    @GetMapping("/index")
    public String adminIndex() {
        return "admin/admin_index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(UserContants.LOGINED_USER);
        return "redirect:/admin";
    }

}
