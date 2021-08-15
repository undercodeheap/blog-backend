package com.mc.blog.service;

import com.mc.blog.entity.SysUser;
import com.mc.blog.vo.Result;
import com.mc.blog.vo.UserVo;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据 token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据 账号查询用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据Id获得作者
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);
}
