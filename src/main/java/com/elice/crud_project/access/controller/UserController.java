package com.elice.crud_project.access.controller;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    User user = new User();
    @GetMapping("/") // 로그인 화면 - 확인
    public String loginPage(Model model){
       return "access/access";
    }
    @PostMapping("/") //접속 성공?
    public String access(Model model, @ModelAttribute UserForm userForm , HttpServletResponse response){
        user = userService.getUserByLoginIdANDPassword(userForm.getLoginId(), userForm.getPassword());
        if( user == null) {
            System.out.println("회원이 아닙니다!");
            return "access/access";
        }
        else {
            Cookie idCookie = new Cookie("loginId",userForm.getLoginId());
            response.addCookie(idCookie);
            System.out.println(idCookie.getValue());
            System.out.println(userForm.getLoginId());
            System.out.println(userForm.getPassword());
        return "redirect:/boards";
        }
    }


    @GetMapping("/join") // 회원가입 화면 - 확인
    public String joinPage(Model model){
        return "access/join";
    }

    @PostMapping("/join") // 회원가입
    public String createUser(@ModelAttribute UserForm form){
        User checkuser = new User();

        System.out.println(form.getLoginId());
        System.out.println(form.getPassword());

        checkuser = userService.getUserByLoginId(form.getLoginId());

        if(checkuser == null){
            user.setLoginId(form.getLoginId());
            user.setPassword(form.getPassword());

            int userId = userService.saveUser(user);
            return "redirect:/";
        }
        else {
            System.out.println("아이디가 중복됩니다!");
            return "redirect:/join";
        }
    }

}
