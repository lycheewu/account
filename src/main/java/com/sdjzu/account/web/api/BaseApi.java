package com.sdjzu.account.web.api;

import com.sdjzu.account.enums.ResultEnum;
import com.sdjzu.account.exception.AccountException;
import com.sdjzu.account.web.auth.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.util.Strings;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * API基础类
 * @author lychee
 * @date 2020/2/5 15:04
 */
public class BaseApi {
    @Resource
    protected HttpServletRequest request;

    /**
     * 获取userId
     *
     * @param
     * @return
     */
    protected String getUserId(){
        Claims claims=getToken(request);
        String userId=claims.getAudience();
        return userId;
    }

    /**
     * 获取部门Id departmentId
     *
     * @return
     */
    protected String getDepartmentId(){
        return getToken(request).get("departmentId").toString();
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    private static Claims getToken(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");
        Claims claims = null;

        if (Strings.isNotEmpty(authorization)) {
            //进行校验
            try {
                claims = JWTUtils.getToken(authorization);
            } catch (Exception e) {
                e.printStackTrace();
                throw new AccountException(ResultEnum.INVALID_LOGIN_STATUS);
            }
        }
        return claims;
    }
}
