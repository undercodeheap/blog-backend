package com.mc.blog.controller;

import com.mc.blog.entity.SysUser;
import com.mc.blog.utils.UserThreadLocal;
import com.mc.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }

}
