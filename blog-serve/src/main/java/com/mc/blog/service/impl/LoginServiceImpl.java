package com.mc.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mc.blog.entity.SysUser;
import com.mc.blog.service.JWTService;
import com.mc.blog.service.LoginService;
import com.mc.blog.service.SysUserService;
import com.mc.blog.vo.ErrorCode;
import com.mc.blog.vo.Result;
import com.mc.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JWTService jwtService;

    private static final String salt = "mszlu!@#";

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1、检查参数是否合法
         * 2、根据用户名和密码检查 user 是否存在
         * 3、如果不存在则登录失败，否则生成 token ，返回前端
         * 4、token 嵌入 redis 中，redis token：user信息，设置过期时间
         * （登录认证的时候先认证token是否合法，再去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = jwtService.generateToken(sysUser);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        jwtService.deleteToken(token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1. 参数是否合法
         * 2. 账户是否存在，存在则失败
         * 3. 不存在，生成用户
         * 4. 生成 token
         * 5. 存入 redis，并返回
         * 6. 注意 加上事务，一旦中间的任何过程出现问题， 注册的用户 需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if(StringUtils.isBlank(account)
            || StringUtils.isBlank(password)
            || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser(null, account, 1, "/static/img/logo.b3a48c0.png", System.currentTimeMillis(), 0, "",
                System.currentTimeMillis(), "", nickname, DigestUtils.md5Hex(password + salt), salt, "");
        sysUserService.save(sysUser);

        String token = jwtService.generateToken(sysUser);
        return Result.success(token);
    }
}
