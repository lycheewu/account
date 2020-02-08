package com.sdjzu.account.enums;

import lombok.Getter;

/**
 * @author lychee
 * @date 2020/2/7 15:31
 */
@Getter
public enum AccountStatusEnum {
    PAYMENT(10,"已付款"),
    UNPAID(11,"未付款"),
    RECEIVED(20,"已收款"),
    RECEIVABLE(21,"待收款")
    ;

    private Integer code;

    private String message;

    AccountStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
