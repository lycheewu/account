package com.sdjzu.account.utils;

import com.google.common.base.Strings;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lychee
 * @date 2020/02/04
 */
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param domain   如果传入的domain为空，则默认设置为当前域名
     * @param name
     * @param value
     * @param path     如果传入path为空，默认为“/”
     * @param maxAge   如果传入的maxAge为空，则设置cookie的生命周期与浏览器会话一致
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name,
                                 String value, String path, Integer maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path == null ? "/" : path);
        cookie.setMaxAge(maxAge == null ? -1 : maxAge);
        if (!Strings.isNullOrEmpty(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name,
                                 String value, int maxAge) {
        setCookie(request, response, domain, name, value, null, maxAge);
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name,
                                 String value) {
        setCookie(request, response, domain, name, value, null, null);
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        setCookie(request, response, null, name, value, null, null);
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                 int maxAge) {
        setCookie(request, response, null, name, value, null, maxAge);
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    value = cookie.getValue();
                }
            }
        }
        return value;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        setCookie(request, response, null, name, null, null, 0);
    }

}
