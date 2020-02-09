package com.sdjzu.account.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author lychee
 * @date 2020/2/4 16:32
 */
@Data
public class UserVO {

    /**
     * 用户ID
     */
    @JsonProperty("userId")
    private String userId;

    /**
     * 登录名
     */
    @JsonProperty("loginName")
    private String loginName;

    /**
     * 登录密码
     */
    @JsonProperty("loginPwd")
    private String loginPwd;

    /**
     * 昵称
     */
    @JsonProperty("nickName")
    private String nickName;

    /**
     * 用户职位
     */
    @JsonProperty("userPosition")
    private String userPosition;

    /**
     * 菜单权限
     */
    @JsonProperty("userRole")
    private String userRole;

    /**
     * 用户所在部门ID
     */
    @JsonProperty("departmentId")
    private String departmentId;

    /**
     * 用户所在部门名称
     */
    @JsonProperty("departmentName")
    private String departmentName;

    /**
     * 备注
     */
    @JsonProperty("comment")
    private String comment;
}
