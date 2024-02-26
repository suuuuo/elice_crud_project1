package com.elice.crud_project.access.controller;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/") // 로그인 화면 - 확인
    public String loginPage(Model model){
       return "access/access";
    }

    @GetMapping("/{user_id}") //접속 성공?
    public String access(Model model){
        return "board/boards";
    }

    @GetMapping("/join") // 회원가입 화면 - 확인
    public String joinPage(Model model){
        return "access/join";
    }

    @PostMapping("/join") // 회원가입
    public String createUser(@ModelAttribute UserForm form){
        User user = new User();

        user.setLoginId(form.getLoginId());
        user.setPassword(form.getPassword());

        int userId = userService.saveUser(user);
        return "redirect:/access";
    }

}
