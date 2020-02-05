package com.sdjzu.account.web.auth;

import com.google.common.base.Strings;
import com.sdjzu.account.utils.CookieUtil;
import com.sdjzu.account.web.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

/**
 * 鉴权逻辑工具
 *
 * @author lychee
 * @date 2020/2/4 18:34
 */
@Component
@Slf4j
public class JWTUtils {

//    public static final String HTTP_HEADER_CLIENT_INFO = "FW-Client-Info";
//    public static final String TOKEN_NAME_USER_ID = "jti";
//    public static final String TOKEN_NAME_DEPARTMENT_ID = "departmentId";

    /**
     * 获取客户端信息。所有请求（包括登录）在内
     *
     * @param request HttpServletRequest对象
     */
//    public String getClientInfo(HttpServletRequest request) {
//        // 尝试从get请求参数中获取
//        log.debug("try getting clientInfo from request parameters");
//        headerValue = request.getParameter(HTTP_HEADER_CLIENT_INFO);
//
//        if (Strings.isNullOrEmpty(headerValue)) {
//            // 尝试从cookie中获取
//            // 正常情况下只有本地开发环境会在cookie中存放该信息
//            log.debug("try getting clientInfo from cookie");
//            String encodedData = CookieUtil.getCookie(request, Constants.Login.HTTP_HEADER_CLIENT_INFO);
//            // 进行base64解码
//            try {
//                if (Strings.isNullOrEmpty(encodedData)) {
//                    headerValue = null;
//                } else {
//                    headerValue = new String(Base64.getDecoder().decode(encodedData), Constants.System.CHARSET);
//                }
//            } catch (UnsupportedEncodingException e) {
//                throw new BizRuntimeException(ErrorCode.System.INTERNAL_SERVER_ERROR);
//            }
//        }
//        if (Strings.isNullOrEmpty(headerValue)) {
//            // 没有携带header，抛出异常
//            log.error("header {} is missing", Constants.Login.HTTP_HEADER_CLIENT_INFO);
//            throw new BizRuntimeException(ErrorCode.User.ILLEGAL_ACCESS);
//        } else {
//            return JSON.parseObject(headerValue, ClientInfo.class);
//        }
//    }
    /**
     * 利用jwt生成token
     */
    public static String setToken(String userId, String departmentId) {
        long now = System.currentTimeMillis();
        long exp = now + 1000 * 60 * 60 * 10;//设置10个小时超时
        JwtBuilder jwtBuilder = Jwts.builder().setId("xinxi304")//设置唯一ID
                .setIssuer("lychee")//设置发行人
                .setSubject("account")//设置主题  可以是JSON数据
                .setAudience(userId)//设置用户
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(exp))//过期时间
                .claim("departmentId", departmentId)//自定义
                .signWith(SignatureAlgorithm.HS256, "sdjzu");//设置签名 使用HS256算法，并设置SecretKey(字符串)
        return jwtBuilder.compact();//构建 并返回一个字符串token
    }

    /**
     * 解析token
     */
    public static Claims getToken(String token) {

        Claims claims = Jwts.parser().setSigningKey("sdjzu")
                .parseClaimsJws(token).getBody();//claim is a json map
        return claims;
    }

}
