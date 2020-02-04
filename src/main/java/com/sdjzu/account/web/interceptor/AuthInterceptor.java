package com.sdjzu.account.web.interceptor;

import com.sdjzu.account.enums.ResultEnum;
import com.sdjzu.account.exception.AccountException;
import com.sdjzu.account.web.auth.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lychee
 * @date 2020/02/04
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private JWTUtils jWTUtils;

    public AuthInterceptor(JWTUtils jWTUtils) {
        this.jWTUtils = jWTUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 注意，如果是跨域请求，且带有自定义header，则发起请求之前会先发出一个OPTIONS请求，即PreFlight Request
        // 针对这样的请求我们不做拦截
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        //获取token
        String authorization = request.getHeader("authorization");
        if (authorization == null) {
            //返回登陆
            throw new AccountException(ResultEnum.USER_NOT_LOGIN);
        } else {
            //进行校验
            try {
                Claims claims = JWTUtils.getToken(authorization);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new AccountException(ResultEnum.INVALID_LOGIN_STATUS);
            }
        }
    }
}


