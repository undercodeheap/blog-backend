package com.mc.blog.service;

import com.mc.blog.entity.SysUser;


public interface JWTService {

    String generateToken(SysUser sysUser);

    SysUser checkToken(String token);

    void deleteToken(String token);
}
