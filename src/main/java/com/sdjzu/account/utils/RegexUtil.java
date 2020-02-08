package com.sdjzu.account.utils;

import java.util.regex.Pattern;

/**
 * @author ly
 * @date 2019/7/31
 */
public class RegexUtil {

    private static final String PREFIX_MATCH_PATTERN = "^(%s)";

    /**
     * 正则表达式的编译表示形式
     * 用以匹配前缀查询和不区分大小写
     *
     * @param key
     * @return
     */
    public static Pattern prefixMatch(String key) {
        return Pattern.compile(String.format(PREFIX_MATCH_PATTERN, key), Pattern.CASE_INSENSITIVE);
    }


}
