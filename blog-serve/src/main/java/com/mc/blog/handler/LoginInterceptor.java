package com.mc.blog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mc.blog.entity.SysUser;
import com.mc.blog.service.JWTService;
import com.mc.blog.utils.UserThreadLocal;
import com.mc.blog.vo.ErrorCode;
import com.mc.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor  implements HandlerInterceptor {

    @Autowired
    private JWTService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在执行 controller 方法之前执行
        /**
         * 1. 需要判断请求的路径 是否为handlerMethod （controller方法）
         * 2. 判断token是否为空，如果为空则未登录
         * 3. 如果不为空，则验证 checkToken
         * 4. 如果认证成功 执行即可
         */
        if(!(handler instanceof HandlerMethod)){
            // 可能访问是RequestResourceHandler springboot 程序 访问静态资源， 默认去classpath下static去查询
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("==================== request start ====================");
        String requestURL = request.getRequestURI();
        log.info("request url:{}", requestURL);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("==================== request end ======================");

        if(StringUtils.isBlank(token)){
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        SysUser sysUser = jwtService.checkToken(token);
        if(sysUser == null){
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }

        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 如果不删除 ThreadLocal 中用完的信息，会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
