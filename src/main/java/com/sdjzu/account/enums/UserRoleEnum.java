package com.sdjzu.account.enums;

/**
 * @author lychee
 * @date 2020/2/9 20:43
 */
public enum UserRoleEnum {
    COMPANY(1,"公司老板"),
    DEPARTMENT(2,"部门经理"),
    EMPLOYEE(3,"录单员")
    ;

    private Integer code;

    private String message;

    UserRoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
