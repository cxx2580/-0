package com.card.controller;

import com.card.model.User;
import com.card.po.UserPO;
import com.card.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    // 跳转到登录页
    @GetMapping("/login")
    public String toLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // 登录验证
    @PostMapping("/login")
    public String login(@Valid User user, BindingResult result, HttpSession session, Model model) {
        // 表单验证
        if (result.hasErrors()) {
            return "login";
        }
        // 验证码验证
        String sessionCode = (String) session.getAttribute("validateCode");
        if (!user.getCode().equalsIgnoreCase(sessionCode)) {
            model.addAttribute("codeError", "验证码错误");
            return "login";
        }
        // 用户名密码验证
        UserPO loginUser = userService.login(user);
        if (loginUser == null) {
            model.addAttribute("loginError", "用户名或密码错误");
            return "login";
        }
        // 登录成功，存入Session
        session.setAttribute("loginUser", loginUser);
        return "redirect:/card/list";
    }

    // 跳转到注册页
    @GetMapping("/register")
    public String toRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 注册提交
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        // 检查用户名是否已存在
        if (userService.checkUname(user.getUname())) {
            model.addAttribute("unameError", "用户名已被占用");
            return "register";
        }
        // 注册用户
        boolean success = userService.register(user);
        if (success) {
            return "redirect:/user/login";
        } else {
            model.addAttribute("registerError", "注册失败，请重试");
            return "register";
        }
    }

    // 异步检查用户名是否可用
    @GetMapping("/checkUname")
    @ResponseBody
    public boolean checkUname(@RequestParam String uname) {
        return !userService.checkUname(uname);
    }

    // 跳转到修改密码页
    @GetMapping("/updatePwd")
    public String toUpdatePwd(Model model) {
        model.addAttribute("user", new User());
        return "updatePwd";
    }

    @PostMapping("/updatePwd")
    public String updatePwd(
            @RequestParam("newPwd") String newPwd, // 直接接收新密码
            HttpSession session,
            Model model
    ) {
        // 获取当前登录用户ID
        UserPO loginUser = (UserPO) session.getAttribute("loginUser");
        if (loginUser == null) {
            model.addAttribute("pwdError", "未登录");
            return "updatePwd";
        }

        // 直接调用Service更新密码（不验证旧密码）
        boolean success = userService.forceUpdatePwd(loginUser.getId(), newPwd);
        if (success) {
            session.invalidate(); // 强制登出，让用户重新登录
            return "redirect:/user/login";
        } else {
            model.addAttribute("pwdError", "修改失败");
            return "updatePwd";
        }
    }

    // 安全退出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 销毁Session
        return "redirect:/user/login";
    }
}