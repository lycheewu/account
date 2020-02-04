package com.sdjzu.account.enums;

import lombok.Getter;

/**
 * @author lychee
 * @date 2020/1/11 21:24
 */
@Getter
public enum ResultEnum {
    /**
     * user
     */
    USER_WRONG_PASSWORD(10,"密码错误!"),

    USER_NOT_EXIST(11,"用户不存在"),

    USER_NOT_LOGIN(12,"未登录!"),

    INVALID_LOGIN_STATUS(13,"登陆状态已过期，请重新登录!"),

    ORDER_STATUS_ERROR(14,"订单状态不正确"),

    ORDER_UPDATE_FAIL(15,"订单更新失败"),

    ORDER_DETAIL_EMPTY(16,"订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),

    PARAM_ERROR(18,"参数不正确"),

    CART_EMPTY(19,"购物车为空"),

    ORDER_OWNER_ERROR(20,"该订单不属于当前用户"),

    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
