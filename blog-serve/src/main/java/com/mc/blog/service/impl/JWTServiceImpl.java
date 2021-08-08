package com.mc.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mc.blog.entity.SysUser;
import com.mc.blog.service.JWTService;
import com.mc.blog.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JWTServiceImpl implements JWTService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String generateToken(SysUser sysUser) {
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete("TOKEN_" + token);
    }
}
