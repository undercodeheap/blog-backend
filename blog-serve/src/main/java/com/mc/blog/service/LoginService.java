package com.mc.blog.service;

import com.mc.blog.entity.SysUser;
import com.mc.blog.vo.Result;
import com.mc.blog.vo.params.LoginParam;

public interface LoginService {

    Result login(LoginParam loginParam);

    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
