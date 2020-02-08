package com.sdjzu.account.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @author mindgazer
 * @date 2019/06/26
 */
@Data
public class LoginVO {

    @JsonProperty(value = "loginName", required = true)
    @NotBlank
    private String loginName;

    @JsonProperty(value = "loginPwd", required = true)
    @NotBlank
    private String loginPwd;

    @JsonProperty(value = "userRole")
    private String userRole;

    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "userId")
    private String userId;

    @JsonProperty(value = "nickName")
    private String nickName;

    @JsonProperty(value = "departmentId")
    private String departmentId;

    @JsonProperty(value = "departmentName")
    private String departmentName;
}
