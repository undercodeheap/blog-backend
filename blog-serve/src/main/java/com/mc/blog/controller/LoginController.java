package com.mc.blog.controller;

import com.mc.blog.service.LoginService;
import com.mc.blog.vo.Result;
import com.mc.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        // 登录，验证用户 不要用 userService，而是单独的业务
        return loginService.login(loginParam);
    }
}
