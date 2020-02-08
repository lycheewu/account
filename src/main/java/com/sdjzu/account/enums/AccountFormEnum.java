package com.sdjzu.account.enums;

import lombok.Getter;

/**
 * @author lychee
 * @date 2020/2/7 15:24
 */
@Getter
public enum AccountFormEnum {
    RECEIVE(1,"收款"),
    PAY(2,"付款")
    ;

    private Integer code;

    private String message;

    AccountFormEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
