package com.sdjzu.account.base.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lychee
 * @date 2020/2/7 20:48
 */
@Getter
@Setter
public class BaseQuery {
    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 分页
     */
    private Integer page;
    private Integer size;

}
