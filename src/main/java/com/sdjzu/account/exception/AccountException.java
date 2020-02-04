package com.sdjzu.account.exception;

import com.sdjzu.account.enums.ResultEnum;

/**
 * @author lychee
 * @date 2020/1/11 21:22
 */
public class AccountException extends RuntimeException {
    private Integer code;

    public AccountException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = code;
    }

    public AccountException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
