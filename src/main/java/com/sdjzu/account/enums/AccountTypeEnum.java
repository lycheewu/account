package com.sdjzu.account.enums;

import lombok.Getter;

/**
 * @author lychee
 * @date 2020/2/7 15:28
 */
@Getter
public enum AccountTypeEnum {
    CASH(1,"现金"),
    ONLINE(2,"线上支付")
    ;

    private Integer code;

    private String message;

    AccountTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
